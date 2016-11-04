package fr.univ_lyon1.etu.ewide.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.univ_lyon1.etu.ewide.model_temp.Version;

import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 * @author Emul0rd
 *
 */
@Controller
public class GitController {

	/**
	 * 
	 * gitCreate cree le repo associe a un nouveau projet, dans le dossier stockant tous les repos.
	 * 
	 * @param projectID : L'ID du projet en BDD a creer sur le disque
	 * @throws IllegalStateException
	 * @throws GitAPIException : Probleme de creation de repo
	 * @throws IOException : Probleme d'acces disque
	 */
	protected void gitCreate(int projectID) throws IllegalStateException, GitAPIException, IOException {
		
		new File("/GitRepos/" + projectID + "/").mkdirs();			// Creation du repo du projet.
		File directory = new File("/GitRepos/" + projectID + "/"); 
		Git.init().setDirectory( directory ).call();				// Creation d'un git pour le repo.
	}
	
	/**
	 * 
	 * gitAdd cree un fichier vide de chemin spécifié en paramètre,  un projet donné.
	 * 
	 * @param projectID : L'ID du projet a modifier
	 * @param fileToCreate : Le chemin vers le fichier a ajouter, ex: "src/main/java/main.java"
	 * @param UserID : L'ID de l'utilisateur qui a cree le nouveau fihcier
	 * @throws IllegalStateException
	 * @throws GitAPIException
	 * @throws IOException
	 */
	protected void gitAdd(int projectID, String fileToCreate, int UserID) throws IllegalStateException, GitAPIException, IOException {
		
		File directory = new File("/GitRepos/" + projectID + "/");
		new File("/GitRepos/" + projectID + "/" + fileToCreate).mkdirs(); 							// Creation des repertoires du fichier si nécessaire
		File file = new File("/GitRepos/" + projectID + "/" + fileToCreate); 
		file.createNewFile();  																		// Creation du fichier en lui-meme
		Git git = Git.init().setDirectory( directory ).call(); 
		DirCache index = git.add().addFilepattern( fileToCreate ).call();
		RevCommit commit = git.commit().setMessage( "Nouveau fichier" + fileToCreate  ).call(); 	// Commit d'une nouvelle version du projet avec le fichier vide.

	}
	
	/**
	 * 
	 * gitGetLastFileVersion renvoie une chaine de caracteres, 
	 * contenant la derniere version du fichier du projet passes tous deux en parametres.
	 * 
	 * @param projectID
	 * @param fileToGet
	 * @return Le fichier dans sa derniere version.
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws RevisionSyntaxException
	 * @throws NoHeadException
	 * @throws GitAPIException
	 */
	protected String gitGetLastFileVersion(int projectID, String fileToGet) throws IOException, InterruptedException, RevisionSyntaxException, NoHeadException, GitAPIException {

		System.getenv("PATH"); 															// Recupere la variable d'environnement PATH
		File directory = new File("/GitRepos/" + projectID + "/");
		Git git = Git.init().setDirectory( directory ).call();							// Appel du git sur le repetoire du projet
		
        Repository repository = new FileRepository("/GitRepos/" + projectID + "/.git");	 
        Iterable<RevCommit> revCommits = git.log()
                .add(repository.resolve("refs/heads/master"))
                .call();																// Création d'un objet contenant un itérateur sur tous les commits du projet
        
        Iterator<RevCommit> it = revCommits.iterator();
        StringBuilder text = new StringBuilder();
        if(it.hasNext()){																// Iterateur place sur la derniere version du projet
        	
        	ProcessBuilder builder = new ProcessBuilder( "git", "show", it.next().getName() + ":" + fileToGet); // Appel a git pour recuperer le fichier
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

		return text.toString();//.replace("\n", "<br/>").replace("\r\n","<br/>");  		//String contenant le fichier

    }
	
	/**
	 * 
	 * Commit du fichier du projet passes en parametres.
	 * 
	 * @param projectID : ID du projet a commiter
	 * @param fileToGet	: Nom du dernier fichier modifie
	 * @param message : Message tape par l'utilisateur en guise de commentaire
	 * @param UserID : ID de l'User auteur du commit
	 * @return La derniere version sauvegardee du fichier, si tout s'est passe correctement.
	 * @throws IllegalStateException
	 * @throws GitAPIException
	 * @throws IOException
	 * @throws RevisionSyntaxException
	 * @throws InterruptedException
	 */
	protected String gitCommit(int projectID, String fileToGet, String message, int UserID) throws IllegalStateException, GitAPIException, IOException, RevisionSyntaxException, InterruptedException {
		
		File directory = new File("/GitRepos/" + projectID + "/");		
		Git git = Git.init().setDirectory( directory ).call();
		DirCache index = git.add().addFilepattern( fileToGet ).call();	
		RevCommit commit = git.commit().setMessage( message ).call();

		return gitGetLastFileVersion(projectID, fileToGet);
	}
	
	//************** TO DOOOOOOO ********************
	/*@RequestMapping(value="/versions/{projectID}/{fileName}", method = RequestMethod.GET)
	protected String gitVersions(ModelMap Model, @PathVariable("projectID") int projectID, @PathVariable("fileName") String fileName) throws Exception {
		//java.net.URLDecoder.decode(fileName, "UTF-8");
		System.getenv("PATH");
		File directory = new File("/GitRepos/" + projectID + "/");
		Git git = Git.init().setDirectory( directory ).call();
		
        Repository repository = new FileRepository("/GitRepos/" + projectID + "/");
        Iterable<RevCommit> revCommits = git.log()
                .add(repository.resolve("refs/heads/master"))
                .call();
        List<Version> versions_list;
        for(RevCommit revCommit : revCommits){
            Version current_version = new Version(0,"UserName bidon", revCommit.getName(), "00:00:00", revCommit.getFullMessage());
            
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

    		s.close();

    		int result = process.waitFor();
        }
        
        
        //Model.addAttribute("git", ("<b>Versions:</b> <br/> " + out));
		return "file_versions";

    }*/
	
	/*@RequestMapping(value ="/env",  method = RequestMethod.GET)
	protected String env(ModelMap Model) {

		String path = System.getenv("PATH");
        
        
        Model.addAttribute("git", ("<b>test:</b> <br/> " + path));
		return "git";

    }*/
	
}