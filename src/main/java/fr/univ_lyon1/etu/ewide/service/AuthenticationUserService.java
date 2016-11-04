package fr.univ_lyon1.etu.ewide.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.univ_lyon1.etu.ewide.dao.UserDAO;

@Service("authenticationUserSerive")
@Configurable
public class AuthenticationUserService implements UserDetailsService {

	@Autowired
	private UserDAO userDAO;

	@Autowired 
	AuthenticationManager authenticationManager;

	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		fr.univ_lyon1.etu.ewide.model_temp.User user = userDAO.getUserByEmail(username);
		List<GrantedAuthority> authorities = buildUserAuthority();
		return buildUserForAuthentication(user, authorities);
	}

	@Transactional(readOnly=true)
	public fr.univ_lyon1.etu.ewide.model_temp.User getCurrentUser(){

		User spring_user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		fr.univ_lyon1.etu.ewide.model_temp.User user = userDAO.getUserByEmail(spring_user.getUsername());
		return user;
	}

	public void doLogin(String username, String password, HttpServletRequest request) {

		try {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
			token.setDetails(new WebAuthenticationDetails(request));
			Authentication authentication = this.authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (Exception e) {
			SecurityContextHolder.getContext().setAuthentication(null);
			e.printStackTrace();
		}

	}

	public boolean isCurrentUserLogged(){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {

			return true;
		}
		
		return false;
	}

	private User buildUserForAuthentication(fr.univ_lyon1.etu.ewide.model_temp.User user,
			List<GrantedAuthority> authorities)
	{
		return new User(user.getMail(), user.getPwd(),
				true, true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority() {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		setAuths.add(new SimpleGrantedAuthority("ROLE_USER"));

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(setAuths);

		return authorities;
	}


}
