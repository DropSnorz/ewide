package fr.univ_lyon1.etu.ewide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import fr.univ_lyon1.etu.ewide.dao.RoleDAO;
import fr.univ_lyon1.etu.ewide.model.Role;
import fr.univ_lyon1.etu.ewide.model.User;

@Component("userRoleService")
public class UserRoleService {

	@Autowired
	private RoleDAO roleDAO;

	@Autowired
	private AuthenticationUserService authenticationUserService;



	public boolean isManager(int projectId) {

		Role role = fetchUserRoleForProjectId(projectId);
		if(role != null && role.getRole().equals("MANAGER")){
			return true;
		}
		else{
			return false;

		}
	}

	public boolean isDeveloper(int projectId) {
		Role role = fetchUserRoleForProjectId(projectId);
		if(role != null &&  role.getRole().equals("DEVELOPER")){
			return true;
		}
		else{
			return false;

		}
	}

	public boolean isReporter(int projectId){
		Role role = fetchUserRoleForProjectId(projectId);
		if(role != null && role.getRole().equals("REPORTER")){
			return true;
		}
		else{
			return false;

		}
	}

	public boolean isMember(int projectId){
		Role role = fetchUserRoleForProjectId(projectId);
		if(role != null){

			return true;
		}
		else{

			return false;

		}
	}

	private Role fetchUserRoleForProjectId(int projectId){

		User user = authenticationUserService.getCurrentUser();
		return roleDAO.getRoleByUserIdAndProjectId(user.getUserID(), projectId);
	}
}
