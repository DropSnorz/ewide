package fr.univ_lyon1.etu.ewide.config;


import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
 
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
 
    @Autowired
	UserDetailsService userDetailsService;
	
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {

    	auth.inMemoryAuthentication().withUser("admin@ewide.com").password("root123").roles("ADMIN");
    	auth.userDetailsService(userDetailsService);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
      	.antMatchers("/dashboard").access("hasRole('USER')")
      	.antMatchers("/project").access("hasRole('USER')")
        .and().formLogin().loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/dashboard", true)
        .usernameParameter("email").passwordParameter("password")
        .and().csrf()
       	.and().exceptionHandling().accessDeniedPage("/Access_Denied");
    }
 
}