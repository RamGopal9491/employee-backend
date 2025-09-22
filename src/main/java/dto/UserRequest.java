package dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class UserRequest {
	
	@Id
	@Column(name="email")
	String email;
	
	@Column(name="password")
	String password;
	
	private String role;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getPassword() {
		return password;
	}
	
}
