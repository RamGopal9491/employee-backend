package klu.model;

import jakarta.persistence.*;

@Entity
@Table(name = "employee_performance")
public class EmployeePerformance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;     // Employee name
    private String department;   // Department name
    private Integer rating;      // 1–5
    @Column(length = 2000)
    private String feedback;     // Feedback notes

    public EmployeePerformance() {}

    public EmployeePerformance(String fullName, String department, Integer rating, String feedback) {
        this.fullName = fullName;
        this.department = department;
        this.rating = rating;
        this.feedback = feedback;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
}
