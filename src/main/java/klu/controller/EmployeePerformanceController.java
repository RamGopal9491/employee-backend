package klu.controller;

import klu.model.EmployeePerformance;
import klu.repository.EmployeePerformanceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/performance")
@CrossOrigin(origins = "http://localhost:5173")  // adjust if needed
public class EmployeePerformanceController {

    private final EmployeePerformanceRepository repository;

    public EmployeePerformanceController(EmployeePerformanceRepository repository) {
        this.repository = repository;
    }

    // ✅ Get all performances
    @GetMapping
    public List<EmployeePerformance> getAllPerformances() {
        return repository.findAll();
    }

    // ✅ Get one by id
    @GetMapping("/{id}")
    public ResponseEntity<EmployeePerformance> getPerformance(@PathVariable Long id) {
        Optional<EmployeePerformance> perf = repository.findById(id);
        return perf.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Save new employee performance
    @PostMapping
    public ResponseEntity<EmployeePerformance> createPerformance(@RequestBody EmployeePerformance performance) {
        performance.setId(null);  // Force Hibernate to treat it as NEW (insert, not update)
        return ResponseEntity.ok(repository.save(performance));
    }

    // ✅ Update performance (rating + feedback + department + fullName)
    @PutMapping("/{id}")
    public ResponseEntity<EmployeePerformance> updatePerformance(@PathVariable Long id,
                                                                 @RequestBody EmployeePerformance payload) {
        return repository.findById(id).map(existing -> {
            existing.setRating(payload.getRating());
            existing.setFeedback(payload.getFeedback());
            existing.setDepartment(payload.getDepartment());
            existing.setFullName(payload.getFullName());
            return ResponseEntity.ok(repository.save(existing));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
