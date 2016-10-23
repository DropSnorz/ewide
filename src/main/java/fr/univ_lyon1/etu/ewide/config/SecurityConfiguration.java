package fr.univ_lyon1.etu.ewide.config;


import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
 
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
 
    @Autowired
	UserDetailsService authenticationUserSerive;
	
    
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {

    	auth.inMemoryAuthentication().withUser("admin@ewide.com").password("root123").roles("ADMIN");
    	auth.userDetailsService(authenticationUserSerive);
    	
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
      	.antMatchers("/dashboard").access("hasRole('USER')")
      	.antMatchers("/project").access("hasRole('USER')")
      	.antMatchers("/project/**").access("hasRole('USER')")
      	.antMatchers("/register").permitAll()
        .and().formLogin().loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/dashboard", true)
        .usernameParameter("email").passwordParameter("password")
        .and().csrf()
       	.and().exceptionHandling().accessDeniedPage("/access-denied");
    }
    
    
    @Bean(name="authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
        
    }
 
}