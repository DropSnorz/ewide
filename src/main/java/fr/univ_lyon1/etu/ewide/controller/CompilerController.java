package fr.univ_lyon1.etu.ewide.controller;

import java.io.*;
import java.net.URLDecoder;

import fr.univ_lyon1.etu.ewide.util.ZipUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import fr.univ_lyon1.etu.ewide.dao.ProjectDAO;
import fr.univ_lyon1.etu.ewide.model.Project;
import fr.univ_lyon1.etu.ewide.service.AuthenticationUserService;

import javax.servlet.http.HttpServletResponse;

@Transactional
@Controller
@RequestMapping(value="/project")
public class CompilerController {

	@Autowired
	ProjectDAO projectDAO;

	@Autowired
	AuthenticationUserService authenticationUserSerive;

	private static final int BUFFER_SIZE = 2048;

	private Logger logger = LoggerFactory.getLogger(CompilerController.class);

	/**
	 * add the command to the compiler project attribute
	 * @param compiler (String)
	 * @param projectID (int)
	 * @return 1
	 */
	@RequestMapping(value ="/{projectID}/configCompiler", method = RequestMethod.POST)
	@PreAuthorize("@userRoleService.isMember(#projectID)")
	public @ResponseBody int  postMessages(@RequestParam String compiler, @PathVariable("projectID") int projectID) {
		Project project=projectDAO.getProjectById(projectID);
		projectDAO.setCompiler(project, compiler);
		return 1;
	}

	/**
	 * Return the actual compiler(-tool) of the project
	 * @param projectID of the project
	 * @return compiler in string object
	 */
	@RequestMapping(name = "getCompiler", value = "/{projectID}/getcompiler", method = RequestMethod.GET)
	@PreAuthorize("@userRoleService.isMember(#projectID)")
	public @ResponseBody String getCompiler(@PathVariable("projectID") int projectID) {
		Project project = projectDAO.getProjectById(projectID);
		logger.info("Project {} compiler : {}", project.getName(), project.getCompiler());
		return project.getCompiler();
	}

	/**
	 * Set the compiler command line
	 * @param projectID id of the project you want to set
	 * @param compiler
	 * @param params command line needed to compile in the form of "compiler=foo&mainfile=bar"
	 */
	@RequestMapping(value = "/{projectID}/setcompiler", method = RequestMethod.POST)
	@PreAuthorize("@userRoleService.isMember(#projectID)")
	public @ResponseBody int setCompiler (
			@PathVariable("projectID") int projectID,
			/*@RequestBody String requestBody*/
			@RequestParam(value = "compiler") String compiler,
			@RequestParam(value = "mainfile") String params) {

		Project project = projectDAO.getProjectById(projectID);

		logger.info("Set project {} compiler '{}' with arguments '{}'", projectID, compiler, params);

		// Scotch pour le probleme des ++ qui sont remplaces par des espaces
		if(compiler.equals("g  "))
			compiler = "g++";

		try {
			projectDAO.setCompiler(project, compiler + " " + URLDecoder.decode(params, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * Send zipped project to the client. The zip file is stored in /tmp/EWIDE/
	 * @param response
	 * @param projectID the int ID of the desired project
	 * @throws IOException
	 */
	@RequestMapping(name = "getZippedProject", value = "/{projectID}/resultfiles", produces = "application/zip", method = RequestMethod.GET)
	@PreAuthorize("@userRoleService.isMember(#projectID)")
	public void getZippedProject(HttpServletResponse response, @PathVariable("projectID") int projectID) throws IOException{

		// Project directory
		String env = System.getenv("HOME");
		String os = System.getProperty("os.name").toLowerCase();
		String projectPath;
		if (os.contains("win"))
			projectPath = "GitRepos/" + projectID + "/";
		else
			projectPath = env + "/GitRepos/" + projectID + "/";

		// Temporary directory for zip storage
		String temp = "/tmp/EWIDE/";
		File tmp = new File(temp);
		logger.info("Creating directory for zip save");
		if(tmp.mkdir())
			logger.info("Directory created");
		else
			logger.info("Directory already exists");

		// zip path
		String zipPath = temp + projectID + ".zip";
		// zip the project
		ZipUtil.zipDir(projectPath, zipPath);
		File downloadFile = new File(zipPath);
		FileInputStream inputStream = new FileInputStream(downloadFile);

		// set response headers
		response.setStatus(HttpServletResponse.SC_OK);
		response.addHeader("Content-Disposition", "attachment; filename=\"" + projectID + ".zip\"");
		response.setContentLength((int) downloadFile.length());
		response.setContentType("application/zip");

		// get output stream
		OutputStream outputStream = response.getOutputStream();

		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = -1;

		// Write bytes from file stream to the output stream
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, bytesRead);
		}

		inputStream.close();
		outputStream.close();

	}


	/**
	 * this function will return the error or success of the compilation
	 * @param projectID (int)
	 * @return (String)
	 */

	@RequestMapping(value ="/{projectID}/compile", method = RequestMethod.GET)
	@PreAuthorize("@userRoleService.isMember(#projectID)")
	public @ResponseBody String compile(@PathVariable("projectID") int projectID){

		// get the compiler of the project
		Project project = projectDAO.getProjectById(projectID);
		String compiler = project.getCompiler();

		if(compiler.equals("")) {
			logger.error("Error: No compiler set for project {}", projectID);
			return "No compiler set. Set one before compiling";
		}

		// get the path
		String env = System.getenv("HOME");
		String os = System.getProperty("os.name").toLowerCase();
		String folder;
		if (os.contains("win"))
			folder = "GitRepos/" + projectID + "/";
		else
			folder = env + "/GitRepos/" + projectID + "/";
		// console output to be returned
		String ret = "$ " + compiler + "\n";

		logger.info("Working on directory {}", folder);
		logger.info("Compiling project {} with {}", project.getName(), project.getCompiler());

		// Compiling process

		Process proc;
		try {
			// Execute compiling process with same env as the JVM but in the project directory
			proc = Runtime.getRuntime().exec(compiler, null, new File(folder));

			// Read the output
			//StreamGobbler out = new StreamGobbler(proc.getInputStream(), "OUTPUT", ret);
			BufferedReader output =
					new BufferedReader(new InputStreamReader(proc.getInputStream()));
			//out.start();

			// Read the err
			//StreamGobbler err = new StreamGobbler(proc.getErrorStream(), "ERROR", ret);
			BufferedReader err =
					new BufferedReader(new InputStreamReader(proc.getErrorStream()));
			//err.start();

			String outLine = "", errLine = "";
			while((outLine = output.readLine()) != null || (errLine = err.readLine()) != null) {
				if (!outLine.equals(""))
					ret += (outLine + "\n");
				if (!errLine.equals(""))
					ret += (errLine + "\n");
			}

			int exitValue = proc.waitFor();
			ret += "Process returned value " + exitValue + "\n";

			return ret;
		} catch (IOException e) {
			logger.error("Error: Cannot launch compiling process ", e);
			return "Error: Cannot launch compiling process";
		} catch (InterruptedException e) {
			logger.error("Error: InterruptedException ", e);
			return "Error: InterruptedException";
		}

	}
}