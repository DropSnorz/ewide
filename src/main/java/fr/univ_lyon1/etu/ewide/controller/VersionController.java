package fr.univ_lyon1.etu.ewide.controller;

import fr.univ_lyon1.etu.ewide.dao.UserDAO;
import fr.univ_lyon1.etu.ewide.dao.VersionDAO;
import fr.univ_lyon1.etu.ewide.dao.RoleDAO;
import fr.univ_lyon1.etu.ewide.model.Version;
import fr.univ_lyon1.etu.ewide.service.GitService;

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
public class VersionController {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private VersionDAO versionDAO;
	
	@Autowired
	private GitService gitService;
	
	@RequestMapping(value="/project/{projectID}/versions", params = {"filename", "ext"}, method = RequestMethod.GET)
	public String gitVersions(ModelMap Model, @PathVariable("projectID") int projectID, @RequestParam(value = "filename") String fileName, @RequestParam(value = "ext") String extension) throws Exception {

		System.getenv("PATH");
		File directory = new File("/GitRepos/" + projectID + "/");
		Git git = Git.init().setDirectory( directory ).call();																			// Ouverture du repo
		
        Repository repository = new FileRepository("/GitRepos/" + projectID + "/.git");													
        Iterable<RevCommit> revCommits = git.log()
                .add(repository.resolve("refs/heads/master"))
                .call();																												// Creation d'un iterateur sur les versions du projet
        List<Version> versions_list = new ArrayList<Version>();																			// Objet voue a contenir la liste des version du fichier
        for(RevCommit revCommit : revCommits){
        	
            ProcessBuilder builder = new ProcessBuilder( "git", "show", revCommit.getName() + ":" + fileName + "." + extension); // récupération du fichier passé en URL dans la version pointee par l'iterateur
    		//System.out.print(revCommit.getName() + ":" + fileName + "." + extension);
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
    		
    		if (! text.toString().contains("fatal: Path '" + fileName + "." + extension + "' exists on disk, but not in '" + revCommit.getName() + "'.")) {
    			System.out.println(revCommit.getName());
    			
    			Version version = versionDAO.getVersionByGitID(revCommit.getName());
    			if (version != null) {
    				System.out.println(version.getUser().getMail());
    				version.setDate( new SimpleDateFormat("dd-MM-yyyy \n HH:mm:ss").format(new Date(revCommit.getCommitTime() * 1000L)));
					version.setComment(revCommit.getFullMessage());
					version.setContent(text.toString());
					
					versions_list.add( version ); // Ajout de la version générée à la liste des versions*/
    			}
    		}
    	}
        if (! versions_list.isEmpty()){
        	Model.addAttribute("versions_list", versions_list);
        }
		return "file_versions";

    }
	
	/*@RequestMapping(value ="/env",  method = RequestMethod.GET)
	protected String env(ModelMap Model) {

		String path = System.getenv("PATH");
        
        
        Model.addAttribute("git", ("<b>test:</b> <br/> " + path));
		return "git";

    }*/
	
}