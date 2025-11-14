package klu.controller;

import klu.model.AssignRole;
import klu.repository.AssignRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*") // Allow React frontend
@RestController
@RequestMapping("/api/assignroles")
public class AssignRoleController {

    @Autowired
    private AssignRoleRepository assignRoleRepository;

    // ✅ GET all roles
    @GetMapping
    public List<AssignRole> getAll() {
        return assignRoleRepository.findAll();
    }

    // ✅ POST: create a new AssignRole
    @PostMapping
    public ResponseEntity<AssignRole> createAssignRole(@RequestBody AssignRole assignRole) {
        // Ensure Hibernate treats this as new
        assignRole.setId(null);

        if (assignRole.getFullName() == null || assignRole.getRole() == null) {
            return ResponseEntity.badRequest().build();
        }

        AssignRole saved = assignRoleRepository.save(assignRole);
        return ResponseEntity.ok(saved);
    }

    // ✅ PUT: update role by ID
    @PutMapping("/{id}/role")
    public ResponseEntity<AssignRole> updateRole(
            @PathVariable Long id,
            @RequestBody RoleRequest request) {

        Optional<AssignRole> opt = assignRoleRepository.findById(id);
        if (opt.isPresent()) {
            AssignRole ar = opt.get();
            ar.setRole(request.getRole());
            AssignRole updated = assignRoleRepository.save(ar);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ DELETE: delete role by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        Optional<AssignRole> opt = assignRoleRepository.findById(id);
        if (opt.isPresent()) {
            assignRoleRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // --- Inner DTO for update request ---
    static class RoleRequest {
        private String role;

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }
}
