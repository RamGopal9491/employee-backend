package klu.model;

import jakarta.persistence.*;

@Entity
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ Link to employee ID
    private Long empid;

    private String name;
    private String department;
    private double salary;

    public Payroll() {}

    public Payroll(Long empid, String name, String department, double salary) {
        this.empid = empid;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEmpid() { return empid; }
    public void setEmpid(Long empid) { this.empid = empid; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
}
