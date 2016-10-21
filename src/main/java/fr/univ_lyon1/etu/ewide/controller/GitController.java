package fr.univ_lyon1.etu.ewide.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

@Controller
public class GitController {

	
	@RequestMapping(value ="/git/create",  method = RequestMethod.GET)
	public String gitCreate(ModelMap Model) throws IllegalStateException, GitAPIException, IOException {
		
		new File("/GitRepos/GitRepo1/").mkdirs();
		File directory = new File("/GitRepos/GitRepo1/");
		Git git = Git.init().setDirectory( directory ).call();
		
		Model.addAttribute("git", "Le git a été créé avec succès.");
		return "git";
	}
	
	@RequestMapping(value ="/git/add",  method = RequestMethod.GET)
	public String gitAdd(ModelMap Model) throws IllegalStateException, GitAPIException, IOException {
		
		File directory = new File("/GitRepos/GitRepo1/");
		File file = new File("/GitRepos/GitRepo1/hello_world.java");
		file.createNewFile();
		Git git = Git.init().setDirectory( directory ).call();
		DirCache index = git.add().addFilepattern( "hello_world.java" ).call();
		RevCommit commit = git.commit().setMessage( "Ajout hello_world.java" ).call();


		Model.addAttribute("git", ("Un fichier <b>hello_world.java</b> vide a bien été ajouté au git. <br/> Trace: " + commit.getFullMessage()));
		return "git";
	}
	
	//@RequestMapping(value ="/git/lastversion",  method = RequestMethod.GET)
	public String getLastVersion() throws IOException, InterruptedException, RevisionSyntaxException, NoHeadException, GitAPIException {

		System.getenv("PATH");
		File directory = new File("/GitRepos/GitRepo1/");
		Git git = Git.init().setDirectory( directory ).call();
		
        Repository repository = new FileRepository("/GitRepos/GitRepo1/.git");
        Iterable<RevCommit> revCommits = git.log()
                .add(repository.resolve("refs/heads/master"))
                .call();
        
        StringBuilder text = new StringBuilder();
        
        Iterator<RevCommit> it = revCommits.iterator();
        if(it.hasNext()){
        	
        	ProcessBuilder builder = new ProcessBuilder( "git", "show", it.next().getName() + ":hello_world.java");
     		builder.directory( directory.getAbsoluteFile() );
     		builder.redirectErrorStream(true);
     		Process process =  builder.start();

     		Scanner s = new Scanner(process.getInputStream());
     		while (s.hasNextLine()) {
     		  text.append(s.nextLine());
     		  text.append("\n");
     		}
     		s.close();

     		process.waitFor();
     		
     		
        }

		return text.toString();//.replace("\n", "<br/>").replace("\r\n","<br/>");

    }
	
	@RequestMapping(value ="/git/commit",  method = RequestMethod.GET)
	public String gitCommit(ModelMap Model) throws IllegalStateException, GitAPIException, IOException, RevisionSyntaxException, InterruptedException {
		
		File directory = new File("/GitRepos/GitRepo1/");
		Git git = Git.init().setDirectory( directory ).call();
		DirCache index = git.add().addFilepattern( "hello_world.java" ).call();
		RevCommit commit = git.commit().setMessage( "Modified hello_world.java" ).call();


		Model.addAttribute("git", ("Le fichier <b>hello_world.java</b> a bien été commit. <br/> Trace: " + commit.getFullMessage() + "<br/><br/>" + getLastVersion()));
		return "git";
	}
	
	@RequestMapping(value ="/git/versions",  method = RequestMethod.GET)
	public String gitVersions(ModelMap Model) throws Exception {

		System.getenv("PATH");
		File directory = new File("/GitRepos/GitRepo1/");
		Git git = Git.init().setDirectory( directory ).call();
		
        Repository repository = new FileRepository("/GitRepos/GitRepo1/.git");
        Iterable<RevCommit> revCommits = git.log()
                .add(repository.resolve("refs/heads/master"))
                .call();
        String out = "<br/>";
        for(RevCommit revCommit : revCommits){
            out += ("<b>"+ revCommit.getName() + ":</b><br/>");
            
            ProcessBuilder builder = new ProcessBuilder( "git", "show", revCommit.getName() + ":" + "hello_world.java");
    		builder.directory( new File( "/GitRepos/GitRepo1/" ).getAbsoluteFile() );
    		builder.redirectErrorStream(true);
    		Process process =  builder.start();

    		Scanner s = new Scanner(process.getInputStream());
    		StringBuilder text = new StringBuilder();
    		while (s.hasNextLine()) {
    		  text.append(s.nextLine());
    		  text.append("\n");
    		}
    		out += text + "<br/><br/>";	
    		s.close();

    		int result = process.waitFor();
        }
        
        
        Model.addAttribute("git", ("<b>Versions:</b> <br/> " + out));
		return "git";

    }
	
	/*@RequestMapping(value ="/env",  method = RequestMethod.GET)
	public String env(ModelMap Model) {

		String path = System.getenv("PATH");
        
        
        Model.addAttribute("git", ("<b>test:</b> <br/> " + path));
		return "git";

    }*/
	
}