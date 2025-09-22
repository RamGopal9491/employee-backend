	package klu.model;
	
	import jakarta.persistence.*;
	@Entity
	@Table(name="users")
	public class Users {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "empid")
	    private Long empid;
	
	    @Column(name="fullname", nullable = false)
	    private String fullname;
	
	    @Column(name="email", unique = true, nullable = false)
	    private String email;
	
	    @Column(name="password", nullable = false)
	    private String password;
	
	    private String role;
	
	    // ✅ getters & setters
	    public Long getEmpid() { return empid; }
	    public void setEmpid(Long empid) { this.empid = empid; }
	
	    public String getFullname() { return fullname; }
	    public void setFullname(String fullname) { this.fullname = fullname; }
	
	    public String getEmail() { return email; }
	    public void setEmail(String email) { this.email = email; }
	
	    public String getPassword() { return password; }
	    public void setPassword(String password) { this.password = password; }
	
	    public String getRole() { return role; }
	    public void setRole(String role) { this.role = role; }
	}
