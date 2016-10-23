package fr.univ_lyon1.etu.ewide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import fr.univ_lyon1.etu.ewide.Model.Role;
import fr.univ_lyon1.etu.ewide.Model.User;
import fr.univ_lyon1.etu.ewide.dao.RoleDAO;

@Component("userRoleService")
public class UserRoleService {

	@Autowired
	private RoleDAO roleDAO;

	@Autowired
	private AuthenticationUserService authenticationUserService;



	public boolean isManager(String projectId) {

		Role role = fetchUserRoleForProjectId(projectId);
		if(role != null && role.getRole().equals("MANAGER")){
			return true;
		}
		else{
			return false;

		}
	}

	public boolean isDevelopper(String projectId) {
		Role role = fetchUserRoleForProjectId(projectId);
		if(role != null &&  role.getRole().equals("DEVELOPPER")){
			return true;
		}
		else{
			return false;

		}
	}

	public boolean isReporter(String projectId){
		Role role = fetchUserRoleForProjectId(projectId);
		if(role != null && role.getRole().equals("REPORTER")){
			return true;
		}
		else{
			return false;

		}
	}

	public boolean isMember(String projectId){
		System.out.println(projectId);
		Role role = fetchUserRoleForProjectId(projectId);
		if(role != null){

			return true;
		}
		else{

			return false;

		}
	}

	private Role fetchUserRoleForProjectId(String projectId){

		User user = authenticationUserService.getCurrentUser();
		return roleDAO.getRoleByUserIdAndProjectId(user.getUserID(), Integer.parseInt(projectId));
	}
}
