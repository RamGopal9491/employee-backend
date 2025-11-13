package klu.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "empid")
    private Long empid;

    @Column(name = "fullname", nullable = false)
    private String fullname;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    private String role;
    private String department;
    private String marital;

    @Lob
    @Column(name = "photo", columnDefinition = "LONGTEXT")
    private String photo; // ✅ base64 encoded photo

    // Getters & setters
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

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getMarital() { return marital; }
    public void setMarital(String marital) { this.marital = marital; }

    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }
}
