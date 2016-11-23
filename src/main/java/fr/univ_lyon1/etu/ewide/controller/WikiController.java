package fr.univ_lyon1.etu.ewide.controller;

import fr.univ_lyon1.etu.ewide.dao.ProjectDAO;
import fr.univ_lyon1.etu.ewide.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import java.io.IOException;

/**
 * Created by kylaste on 17/11/16.
 */

@Controller
@Transactional
@RequestMapping(value="/project/{projectId}/wiki")
public class WikiController extends BaseProjectController{

     /*
	  * This function allow to create project in the database
	  * Also create a new git repository
	 */

    @Autowired
    public ProjectDAO projectDAO;

    /**
     * gives the wiki
     * @param Model
     * @return view usersmanager
     * @throws IOException
     */
    @PreAuthorize("@userRoleService.isMember(#projectId)")
    @RequestMapping(value="")
    public ModelAndView wiki(ModelMap Model, @PathVariable("projectId") int projectId) throws IOException{

        ModelAndView model = new ModelAndView("wiki/wiki");
        String wiki = projectDAO.getProjectById(projectId).getWiki();

        //wiki of the project
        model.addObject("wiki",wiki);

        return model;
    }


    @PreAuthorize("@userRoleService.isMember(#projectId)")
    @RequestMapping(value="/edit", method = RequestMethod.GET)
    public ModelAndView getWikiEdit(@PathVariable("projectId") int projectId){

        ModelAndView model = new ModelAndView("wiki/wiki-edit");
        String wiki = projectDAO.getProjectById(projectId).getWiki();
        model.addObject("wiki", wiki);

        return model;
    }

    @PreAuthorize("@userRoleService.isMember(#projectId)")
    @RequestMapping(value="/edit", method = RequestMethod.POST)
    public ModelAndView postWikiEdit(@PathVariable("projectId") int projectId,
                                     @RequestParam("wikiText") final String text,
                                     RedirectAttributes redirectAttributes){

        ModelAndView view = new ModelAndView("wiki/wiki-edit");

        Project project = projectDAO.getProjectById(projectId);
        project.setWiki(text);

        projectDAO.updateWiki(project.getProjectID(), text);
        redirectAttributes.addFlashAttribute("SUCCESS_MESSAGE", "Wiki successfully updated !");
        view.setViewName("redirect:../wiki/");

        return view;
    }
}
