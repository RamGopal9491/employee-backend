package klu.model;

import jakarta.persistence.*;

@Entity
@Table(name = "assign_roles")
public class AssignRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String role; // Employee, Manager, Admin, HR

    public AssignRole() {}

    public AssignRole(String fullName, String role) {
        this.fullName = fullName;
        this.role = role;
    }

    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
