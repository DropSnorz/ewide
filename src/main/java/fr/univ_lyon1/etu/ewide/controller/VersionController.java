package fr.univ_lyon1.etu.ewide.controller;

import fr.univ_lyon1.etu.ewide.dao.UserDAO;
import fr.univ_lyon1.etu.ewide.dao.VersionDAO;
import fr.univ_lyon1.etu.ewide.dao.ProjectDAO;
import fr.univ_lyon1.etu.ewide.dao.RoleDAO;
import fr.univ_lyon1.etu.ewide.model.Version;
import fr.univ_lyon1.etu.ewide.service.GitService;
import fr.univ_lyon1.etu.ewide.model.Project;
import fr.univ_lyon1.etu.ewide.model.User;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 * @author Steven
 *
 */
@Transactional
@Controller
public class VersionController extends BaseProjectController{
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private VersionDAO versionDAO;
	
	@Autowired ProjectDAO projectDAO;
	
	@Autowired
	private GitService gitService;
	
	public void getFileVersions(ModelMap model, String fileName, int projectId) throws IOException, IllegalStateException, GitAPIException, InterruptedException{
		System.getenv("PATH");
		File directory = new File(gitService.getReposPath()+ projectId + "/");
		Git git = Git.init().setDirectory( directory ).call();										// Ouverture du repo
		
		Repository repository = new FileRepository(gitService.getReposPath() + projectId + "/.git");		    											
        Iterable<RevCommit> revCommits = git.log()
                .add(repository.resolve("refs/heads/master"))
                .call();																												// Creation d'un iterateur sur les versions du projet
        List<Version> versions_list = new ArrayList<Version>();																			// Objet voue a contenir la liste des version du fichier
        for(RevCommit revCommit : revCommits){
        	
            ProcessBuilder builder = new ProcessBuilder( "git", "show", revCommit.getName() + ":" + fileName); // récupération du fichier passé en URL dans la version pointee par l'iterateur

            builder.directory( directory.getAbsoluteFile() );
    		builder.redirectErrorStream(true);
    		Process process =  builder.start();

    		Scanner s = new Scanner(process.getInputStream());
    		StringBuilder text = new StringBuilder();
    		while (s.hasNextLine()) {
    		  
    		  text.append(s.nextLine());
    		  text.append("\n");
    		}

    		s.close();
    		int result = process.waitFor();
    		
    		if (! text.toString().contains("fatal: Path '"+fileName+"' does not exist in '"+ revCommit.getName() +"'." )
    			&& !text.toString().contains("fatal: Path '"+fileName+"' exists on disk, but not in '"+ revCommit.getName() +"'.")){
    			
    			
    			Version version = versionDAO.getVersionByGitID(revCommit.getName());
    			if (version != null) {
    				
    				version.setDate( new SimpleDateFormat("dd-MM-yyyy \n HH:mm:ss").format(new Date(revCommit.getCommitTime() * 1000L)));
					version.setComment(revCommit.getFullMessage());
					version.setContent(text.toString());
					
					versions_list.add( version ); // Ajout de la version générée à la liste des versions*/
    			}
    		}
    	}
        if (! versions_list.isEmpty()){
        	model.addAttribute("versions_list", versions_list);	
        }
        model.addAttribute("fileName", fileName);
    	model.addAttribute("projectId", projectId);
	}
	
	@RequestMapping(value="/project/{projectId}/versions/{fileName}/display", method = RequestMethod.GET)
	public String gitVersions(ModelMap model, @PathVariable("projectId") int projectId, @PathVariable(value = "fileName") String fileName) throws Exception {
		
		fileName = fileName.replace('?', '/');
		getFileVersions(model, fileName, projectId);
        
		return "file_versions";

    }
	
	
	@RequestMapping(value="/project/{projectId}/versions/{fileName}/restore/{version}", method = RequestMethod.GET)
	public ModelAndView gitRollback(ModelMap model, @PathVariable("projectId") int projectId, @PathVariable("fileName") String fileName, @PathVariable("version") String version) throws Exception {
		
		ModelAndView mv = new ModelAndView("file_versions");
		fileName = fileName.replace('?', '/');
		gitService.gitRollBack(projectId, fileName, version);
		
		getFileVersions(model, fileName, projectId);
		
		
		mv.addObject("versionRestored", version);
		return mv;
	}
	
	@RequestMapping(value="/project/{projectId}/versions/display", method = RequestMethod.GET)
	public String gitProjectVersions(ModelMap model, @PathVariable("projectId") int projectId) throws Exception {
		
		Project project = projectDAO.getProjectById(projectId);	
		List<Version> versions_list = versionDAO.getAllVersionsByProject(project);
		
		System.getenv("PATH");
		File directory = new File(gitService.getReposPath()+ projectId + "/");
		Git git = Git.init().setDirectory( directory ).call();							// Ouverture du repo
		
		Repository repository = new FileRepository(gitService.getReposPath() + projectId + "/.git");		    											
        Iterable<RevCommit> revCommits = git.log()
                .add(repository.resolve("refs/heads/master"))
                .call();
        
        for(RevCommit revCommit : revCommits){
        	for(Version v:versions_list) if (v.getVersion().equals(revCommit.getName())) {
        		v.setDate( new SimpleDateFormat("dd-MM-yyyy \n HH:mm:ss").format(new Date(revCommit.getCommitTime() * 1000L)) );
        		v.setComment(revCommit.getFullMessage());
        	}
        }
        
        
		
		
			
		model.addAttribute("versions_list", versions_list);	
		return "project_versions";
	}
	
}