package fr.univ_lyon1.etu.ewide.service;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import fr.univ_lyon1.etu.ewide.dao.ProjectDAO;
import fr.univ_lyon1.etu.ewide.dao.VersionDAO;
import fr.univ_lyon1.etu.ewide.model.Project;
import fr.univ_lyon1.etu.ewide.model.User;
import fr.univ_lyon1.etu.ewide.model.Version;

/**
 * Created by Steven on 14/11/2016.
 */

@Component("gitService")
public class GitService {

	@Autowired
	VersionDAO versionDAO;
	
	@Autowired
	ProjectDAO projectDAO;
	
	@Autowired
	AuthenticationUserService authenticationUserService;
	
	private String gitReposPath = "";
	
	public String getReposPath(){
		System.getenv("HOME");
		return gitReposPath;
	}
	
	/**
	 * 
	 * gitCreate cree le repo associe a un nouveau projet, dans le dossier stockant tous les repos.
	 * 
	 * @param projectID : L'ID du projet en BDD a creer sur le disque
	 * @throws IllegalStateException
	 * @throws GitAPIException : Probleme de creation de repo
	 * @throws IOException : Probleme d'acces disque
	 */
	
	public void gitCreate(int projectID) throws IllegalStateException, GitAPIException, IOException {
		
		new File("/GitRepos/" + projectID + "/").mkdirs();			// Creation du repo du projet.
		File directory = new File("/GitRepos/" + projectID + "/"); 
		Git git = Git.init().setDirectory( directory ).call();				// Creation d'un git pour le repo.
		
		/* Commit 0 pushed to the database */
		
		RevCommit commit = git.commit().setMessage( "Project #" + projectID + " repository creation." ).call();
		User user = authenticationUserService.getCurrentUser();
		Project project = projectDAO.getProjectById(projectID);
		Version version0 = versionDAO.create(commit.name(), project, user, 0);
	}
	
	/**
	 * 
	 * gitAdd cree un fichier vide de chemin specifie en parametre,  un projet donne.
	 * 
	 * @param projectID : L'ID du projet a modifier
	 * @param fileToCreate : Le chemin vers le fichier a ajouter, ex: "src/main/java/main.java"
	 * @param UserID : L'ID de l'utilisateur qui a cree le nouveau fihcier
	 * @throws IllegalStateException
	 * @throws GitAPIException
	 * @throws IOException
	 */
	public void gitAdd(int projectID, String fileToCreate, int UserID) throws IllegalStateException, GitAPIException, IOException {
		
		File directory = new File("/GitRepos/" + projectID + "/");
		new File("/GitRepos/" + projectID + "/" + fileToCreate).mkdirs(); 							// Creation des repertoires du fichier si n�cessaire
		File file = new File("/GitRepos/" + projectID + "/" + fileToCreate); 
		file.createNewFile();  																		// Creation du fichier en lui-meme
		Git git = Git.init().setDirectory( directory ).call(); 
		DirCache index = git.add().addFilepattern( fileToCreate ).call();
		
		RevCommit commit = git.commit().setMessage( "Nouveau fichier" + fileToCreate  ).call(); 	// Commit d'une nouvelle version du projet avec le fichier vide.
		User user = authenticationUserService.getCurrentUser();
		Project project = projectDAO.getProjectById(projectID);
		Version version0 = versionDAO.create(commit.name(), project, user);
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
	public String gitGetLastFileVersion(int projectID, String fileToGet) throws IOException, InterruptedException, RevisionSyntaxException, NoHeadException, GitAPIException {

		System.getenv("PATH"); 															// Recupere la variable d'environnement PATH
		File directory = new File("/GitRepos/" + projectID + "/");
		Git git = Git.init().setDirectory( directory ).call();							// Appel du git sur le repetoire du projet
		
        Repository repository = new FileRepository("/GitRepos/" + projectID + "/.git");	 
        Iterable<RevCommit> revCommits = git.log()
                .add(repository.resolve("refs/heads/master"))
                .call();																// Creation d'un objet contenant un it�rateur sur tous les commits du projet
        
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
	 * Commit the file of a project given as a parameter.
	 * 
	 * @param projectID : ID du projet a commiter
	 * @param fileToGet	: Nom du dernier fichier modifie
	 * @param message : Message tape par l'utilisateur en guise de commentaire
	 * @param UserID : ID de l'User auteur du commit
	 * @return La derniere version du projet, si tout s'est passe correctement.
	 * @throws IllegalStateException
	 * @throws GitAPIException
	 * @throws IOException
	 * @throws RevisionSyntaxException
	 * @throws InterruptedException
	 */
	public RevCommit gitCommit(int projectID, String fileToGet, String message, int UserID) throws IllegalStateException, GitAPIException, IOException, RevisionSyntaxException, InterruptedException {
		
		File directory = new File("/GitRepos/" + projectID + "/");		
		Git git = Git.init().setDirectory( directory ).call();
		DirCache index = git.add().addFilepattern( fileToGet ).call();	
		RevCommit commit = git.commit().setMessage( message ).call();

		return commit;
	}
}
