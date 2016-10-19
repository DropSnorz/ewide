package fr.univ_lyon1.etu.ewide.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.univ_lyon1.etu.ewide.dao.UserDAO;

@Service("userDetailsService")
@Configurable
public class AuthenticationUserService implements UserDetailsService {

	@Autowired
	private UserDAO userDAO;

	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		fr.univ_lyon1.etu.ewide.Model.User user = userDAO.getUserByEmail(username);
		List<GrantedAuthority> authorities = buildUserAuthority();
		return buildUserForAuthentication(user, authorities);
	}

	private User buildUserForAuthentication(fr.univ_lyon1.etu.ewide.Model.User user,
			List<GrantedAuthority> authorities)
	{
		return new User(user.getMail(), user.getPwd(),
				true, true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority() {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		setAuths.add(new SimpleGrantedAuthority("USER"));

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(setAuths);

		return authorities;
	}


}
