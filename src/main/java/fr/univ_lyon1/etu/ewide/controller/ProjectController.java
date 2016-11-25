package fr.univ_lyon1.etu.ewide.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import fr.univ_lyon1.etu.ewide.dao.ProjectDAO;
import fr.univ_lyon1.etu.ewide.service.AuthenticationUserService;
import fr.univ_lyon1.etu.ewide.service.GitService;
import fr.univ_lyon1.etu.ewide.dao.RoleDAO;
import fr.univ_lyon1.etu.ewide.dao.VersionDAO;
import fr.univ_lyon1.etu.ewide.model.Project;
import fr.univ_lyon1.etu.ewide.model.Role;
import fr.univ_lyon1.etu.ewide.model.User;

@Transactional
@Controller
public class ProjectController {
	
	 
	@Autowired
	AuthenticationUserService authenticationUserSerive;
	

	@Autowired	
	public RoleDAO roleDAO;
	
	@Autowired
	public GitService git;
	
	@Autowired
	 public  ProjectDAO projectDAO;
	
	@Autowired
	public VersionDAO versionDAO;
	
	/*
	 * Display all projects of the user connected
	 */
	@RequestMapping(value ="/dashboard", method = RequestMethod.GET)
		public ModelAndView home(ModelMap Model) throws IOException{
	 		User user = authenticationUserSerive.getCurrentUser();
	        	 		
	        List<Project> listProject = roleDAO.getProjectIDByUser(user);
	        
	        ModelAndView model = new ModelAndView("dashboard");
	        model.addObject("projectList", listProject);
	        model.setViewName("dashboard");
	        return model;
	    }
	 /*
	  * Display the screen of projet
	  */
	 @RequestMapping(value ="/newproject", method = RequestMethod.GET)
	 	public String getNewProject()	{
		 	return "new_project";
	 }
	 
	 
	 /* 
	  * This function allow to create project in the database
	  * Also create a new git repository 
	 */



	 @RequestMapping(value ="/newproject", method = RequestMethod.POST)
	 public String addProject (@RequestParam("projectName")String projectName,
			 @RequestParam("projectDesc")String projectDesc,
			 Project project,
			 Role role,
			   ModelMap model) {
		 User user = authenticationUserSerive.getCurrentUser();
		 // Assign many attributes
		 project.setName(projectName);
		 project.setDescription(projectDesc);
		 // Create the project in the database
		 projectDAO.createProject(project);
		 // Create the git repository
		 try {
			git.gitCreate(project.getProjectID());
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
		 // Assign the creator of this project to the role manager
		 role.setUser(user);
		 roleDAO.createRole(user, project,"MANAGER");
		
			     
		 	return "redirect:/dashboard";
			     
	 }

	 /*
	  * Display the editor
	  */
     @RequestMapping(value = {"/project/{projectID}/ide"}, method = RequestMethod.GET)
     @PreAuthorize("@userRoleService.isMember(#projectID)")
     public ModelAndView getProjectByName(@PathVariable("projectID") int projectID){
    	 ModelAndView model = new ModelAndView("dashboard");
    	 Project project = new Project();
    	 project = projectDAO.getProjectById(projectID);
    	 User user = authenticationUserSerive.getCurrentUser();
    	 Role role=roleDAO.getRoleByUserIdAndProjectId(user.getUserID(), projectID);
    	 model.addObject("userrole",role.getRole());
    	 model.addObject("project", project);
    	 model.setViewName("ide");
    	 return model;
             

     }
     /**
      * This function return a JSON file whose permit to create the file tree
      * @param projectID
      * @return
      */
    
     @RequestMapping(value = {"/project/{projectID}/files"},method = RequestMethod.GET, produces="application/json")
     @PreAuthorize("@userRoleService.isMember(#projectID)")
     public @ResponseBody String getProjectByNameJSON(@PathVariable("projectID") int projectID){
    	
    	 Project project = new Project();
    	 project = projectDAO.getProjectById(projectID);
    	 File[] files = new File(git.getReposPath()+projectID).listFiles();
    	 ArrayList<String> test = new ArrayList<String>();
    	 JSONArray jarr = new JSONArray();
    	 jarr = tree(files);
    	 
    	 JSONObject jobjroot = new JSONObject();
    	 jobjroot.put("text", project.getName());
    	 String os = System.getProperty("os.name").toLowerCase();
			if (os.contains("win")){
				jobjroot.put("id","GitRepos/"+projectID );
			}
			else {
				String env = System.getenv("HOME");
				jobjroot.put("id",env + "/GitRepos/"+projectID );
			}
			JSONObject jobjstate = new JSONObject();
	    	jobjstate.put("opened", "true");
	    	jobjstate.put("selected", "true");
			jobjroot.put("state", jobjstate.toString());
			jobjroot.put("children", jarr);
			JSONObject jobjgitid = new JSONObject();
			
			jobjgitid.put("gitid", versionDAO.getProjectLatestVersionNumber(project));
			jobjroot.put("li_attr", jobjgitid);
			
    	 
    	 return jobjroot.toString();
    	
     }
     /**
      * 
      * @param file contains the file path
      * @param projectID 
      * @return jobj the content of the file
      */
     @RequestMapping(value = {"/project/{projectID}/files"}, method = RequestMethod.POST)
     @PreAuthorize("@userRoleService.isMember(#projectID)")
     public @ResponseBody String postFileJSON(@RequestParam String file,@PathVariable("projectID") int projectID){
    	 JSONObject jobj = new JSONObject();
    	 try{
    		 String contents = new String(Files.readAllBytes(Paths.get(file)));
    		 jobj.put("contents", contents);
    	 	return jobj.toString();
    	 } catch (IOException e){
    		 JSONObject jerror = new JSONObject();
    		 jerror.put("type", "error");
    		 jerror.put("message", "Cannot load files");
    		 return jerror.toString();
    	 }
    	 
    	 
     }
     
    
     /**
      * 
      * @param file contains all files modified by user in json format
      * @param projectID
      * @return succes or error message
      */
     @RequestMapping(value = {"/project/{projectID}/save"}, method = RequestMethod.POST)
     @PreAuthorize("@userRoleService.isMember(#projectID)")
     public @ResponseBody String saveProjectJSON(@RequestParam String file,@RequestParam String id,@RequestParam String confirm,@PathVariable("projectID") int projectID){
     	Project project = new Project();
     	project = projectDAO.getProjectById(projectID);
     	int lastid = versionDAO.getProjectLatestVersionNumber(project);
     	String os = System.getProperty("os.name").toLowerCase();
     	String jsonresult;
     	boolean modified = false;
     	JSONArray jarr = new JSONArray(file);
     	if ((Integer.valueOf(id)==lastid) || confirm.equals("1")){
     		jsonresult = save(file,id,projectID);
     	}else{
     		for (int i =0; i<jarr.length();++i){
     			JSONObject jobj = jarr.getJSONObject(i);
     			
     			try{
     				String filePath = jobj.get("id").toString();
     		    	if (os.contains("win")){
     		    		filePath = filePath.replace("\\\\", "\\");
     		    		filePath = filePath.replace("\\", "/");
     		     	}
     	     		filePath = filePath.replaceAll(git.getReposPath()+projectID+"/", "");
     				if ( git.gitGetLastFileVersion(projectID, filePath ) != jobj.get("content").toString() ){ // File is modified
     					modified = true;
         			}
     				
     			}catch (Exception e) {
					// TODO: handle exception
				}
     		
     			
     		}
     		if (!modified){
     			jsonresult = save(file,id, projectID);
     		}else{
     			JSONObject jerror = new JSONObject();
      			jerror.put("type", "error");
      			jerror.put("message", "File has been modified by another user");
      			return jerror.toString();
     		}
     	}
     	return jsonresult;
     	
     	

     	
     }
     
     public String save(String file, String id, int projectID){
     	String os = System.getProperty("os.name").toLowerCase();
     	JSONArray jarr = new JSONArray(file);
      	for (int i = 0; i< jarr.length();++i){
      		try{
 	     		JSONObject jobj = jarr.getJSONObject(i);
 	     		User user = authenticationUserSerive.getCurrentUser();
 	     		File _file = new File(jobj.get("id").toString());
 	     		
 	     		if(!jobj.get("newpath").toString().equals("")){ // Move or rename file ;
 	     			if (!_file.exists())
 	     				throw (new Exception ("File doesn't exists"));
 	     			git.gitRename(projectID, _file.toString(), jobj.get("newpath").toString());
 	     			File temp = new File(jobj.get("id").toString());
 	     			_file.renameTo(new File(jobj.get("newpath").toString()));
 	     			temp.delete();			
 	     		}
 	 
 	     		if(jobj.get("ftype").toString().equals("filetext")){ 
 	     			if (!_file.exists()){
     	     			_file.createNewFile();
     	     		}
     	     		FileWriter fw = new FileWriter(_file);
     	     		BufferedWriter bw = new BufferedWriter(fw);
     	     		bw.write(jobj.get("content").toString());
     				bw.close();
     				String filePath = jobj.get("id").toString();
     		    	if (os.contains("win")){
     		    		filePath = filePath.replace("\\\\", "\\");
     		    		filePath = filePath.replace("\\", "/");
     		     	}
     	     		filePath = filePath.replaceAll(git.getReposPath()+projectID+"/", "");
     	     		git.gitCommit(projectID, filePath, _file.getName()+" has changed or has been changed");
 	     		}
 	     		
 	     		if(jobj.get("ftype").toString().equals("delete")){ // When we delete file or folder
 	     			if (!_file.exists()){
 	     				throw (new Exception ("File doesn't exist"));
 	     			}else{
 		     			if(_file.isDirectory()){ // We must to clean the directory 
 	     					String[]entries = _file.list();
 	     					for(String s: entries){
 	     					    File currentFile = new File(_file.getPath(),s);
 	     					    currentFile.delete();   
 	     					}
 		     			}
 	     				_file.delete();		
 	     			}
 	     		}
 	     		
 	     		if(jobj.get("ftype").toString().equals("folder")){ // Create folder 
 	     			if (_file.exists()){
      					throw (new Exception ("Folder already exists !"));
      				}
      				_file.mkdir();
 	     		}
 	     		
      		 
      		}catch(Exception e){
      			JSONObject jerror = new JSONObject();
      			jerror.put("type", "error");
      			jerror.put("message", e.getMessage());
      			return jerror.toString();
          	}	
      	}
      	JSONObject jsuccess = new JSONObject();
      	jsuccess.put("type", "success");
      	jsuccess.put("message", "Commit success");
 		return jsuccess.toString(); 
     }
     
     public JSONArray tree(File files[]){
    	 ArrayList<String> liste = new ArrayList<String>() ;
    	 JSONArray jarr = new JSONArray();
    	 for (File file: files){
    		 if (file.isDirectory()){
    			 if (!file.getName().equals(".git")){
    				 JSONObject jobj = new JSONObject();
        	    	 jobj.put("id", file.getPath());
        	    	 jobj.put("text", file.getName());
        	    	 
    				 //tree(file.listFiles());
    				jobj.put("children", tree(file.listFiles()));
    				 jarr.put(jobj);
    			 }
    				 
    			 
    		 }else{
    		 if (!file.getName().equals(".git") && !file.getName().contains(".class")){
    			 liste.add(file.getName());
    			 JSONObject jobj = new JSONObject();
    			 jobj.put("id", file.getPath());
    	    	 jobj.put("text", file.getName());
    	    	 jobj.put("icon", "jstree-file");
    	    	 jobj.put("type", "file");
    	    	 
				 jarr.put(jobj);
    		 }
    		 }
    		 
    		 
    	 }
    	 
    	 
    	// System.out.println(jarr.toString());
    	 return jarr;
    	 //return listeFichier;
    	 //System.out.println(liste);
    
     }

	

}
