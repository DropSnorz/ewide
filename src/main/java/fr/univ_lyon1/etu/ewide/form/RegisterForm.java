package fr.univ_lyon1.etu.ewide.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterForm {

    @NotNull
    @Size(min=2, max=30)
    private String username;

    @NotNull
    @Size(min=3, max=30)
    private String email;
    
    @NotNull
    @Size(min=2, max=30)
    private String password;
    
    @NotNull
    @Size(min=2,max=30)
    private String confirmpassword;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String password) {
		this.confirmpassword = password;
	}
    
    

   
}