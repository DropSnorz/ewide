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

		@Autowired
		 public  ProjectDAO projectDAO;
		

	 @RequestMapping(value ="/newproject", method = RequestMethod.POST)
	 public String addProject (@RequestParam("projectName")String projectName,
			 @RequestParam("projectDesc")String projectDesc,
			 Project project,
			 Role role,
			   ModelMap model) {
		 User user = authenticationUserSerive.getCurrentUser();
		 GitService git = new GitService();
		 // Assign many attributes
		 project.setName(projectName);
		 project.setDescription(projectDesc);
		 project.setFileTree(projectName);
		 
		 
		 /*project.setLinkRepo(projectName);*/
		 project.setLinkMakefile(projectName);
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
     @RequestMapping(value = {"/project/{projectID}"}, method = RequestMethod.GET)
     public ModelAndView getProjectByName(@PathVariable("projectID") int projectID){
    	 ModelAndView model = new ModelAndView("dashboard");
    	 Project project = new Project();
    	 project = projectDAO.getProjectById(projectID);
    	
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
     public @ResponseBody String getProjectByNameJSON(@PathVariable("projectID") int projectID){
    	
    	 Project project = new Project();
    	 project = projectDAO.getProjectById(projectID);
    	 File[] files = new File(git.getReposPath()+projectID).listFiles();
    	 ArrayList<String> test = new ArrayList<String>();
    	 JSONArray jarr = new JSONArray();
    	 jarr = tree(files);
    	 
    	 return jarr.toString();
    	
     }
     /**
      * 
      * @param file contains the file path
      * @param projectID 
      * @return jobj the content of the file
      */
     @RequestMapping(value = {"/project/{projectID}/files"}, method = RequestMethod.POST)
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
      * @param file contains all file modified by user in json format
      * @param projectID
      * @return succes or error message
      */
     @RequestMapping(value = {"/project/{projectID}/save"}, method = RequestMethod.POST)
     public @ResponseBody String saveProjectJSON(@RequestParam String file,@PathVariable("projectID") int projectID){
     	String os = System.getProperty("os.name").toLowerCase();
     	if (os.contains("win")){
     		file = file.replace("\\\\", "\\");
     		file = file.replace("\\", "/");
     	}
    	JSONArray jarr = new JSONArray(file);
     	for (int i = 0; i< jarr.length();++i){
     		try{
	     		JSONObject jobj = jarr.getJSONObject(i);
	     		User user = authenticationUserSerive.getCurrentUser();
	     		File _file = new File(jobj.get("id").toString());
	     		if(jobj.get("ftype").toString().equals("filetext")){
    	     		if (!_file.exists()){
    	     			_file.createNewFile();
    	     		}
    	     		FileWriter fw = new FileWriter(_file);
    	     		BufferedWriter bw = new BufferedWriter(fw);
    	     		bw.write(jobj.get("content").toString());
    				bw.close();
    	     		String filePath = jobj.get("id").toString().replaceAll(git.getReposPath()+projectID+"/", "");
    	     		
    	     		
    	     		git.gitCommit(projectID, filePath, "vide");
//    	     			filePath = jobj.get("id").toString().replaceAll("GitRepos/"+projectID+"/", "");
//    	     			 //filePath = jobj.get("id").toString().replaceAll("GitRepos\\"+projectID+"\\", "");	
//    	     		}
	     		}
	     		if(jobj.get("ftype").toString().equals("delete")){ // When we delete file or folder
	     			if (!_file.exists()){
	     				throw (new Exception ("File doesn't exist"));
	     			}else{
	     			if (_file.isDirectory()){ // We must to clean the directory 
	     					String[]entries = _file.list();
	     					for(String s: entries){
	     					    File currentFile = new File(_file.getPath(),s);
	     					    currentFile.delete();
	     					}
	     			}
	     				_file.delete();
	     				
	     			}
	     		}
	     		/*
	     		case "folder": // Create folder 
	     			if (_file.exists()){
     					throw (new Exception ("Folder already exists !"));
     				}
     				_file.mkdir();
     				break;
	     			case "rename": // Rename file or folder
	     				if (_file.exists()){
	     					throw (new Exception ("Cannot rename under this name!"));
	     				}
	     				_file.renameTo(new File(jobj.get("change").toString()));
	     		
	     		
		     		*/
	     		// TODO
	     		// Replace the first occurence only !
     		 
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
    		 if (!file.getName().equals(".git")){
    			 liste.add(file.getName());
    			 JSONObject jobj = new JSONObject();
    			 jobj.put("id", file.getPath());
    	    	 jobj.put("text", file.getName());
    	    	 jobj.put("icon", "jstree-file");
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
