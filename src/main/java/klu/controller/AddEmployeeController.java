package klu.controller;

import klu.model.AddEmployee;
import klu.repository.AddEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // React frontend
@RestController
@RequestMapping("/api/addemployees")
public class AddEmployeeController {

    @Autowired
    private AddEmployeeRepository repo;

    // Add new employee
    @PostMapping
    public AddEmployee addEmployee(@RequestBody AddEmployee emp) {
        return repo.save(emp);
    }

    // Get all employees
    @GetMapping
    public List<AddEmployee> getAllEmployees() {
        return repo.findAll();
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public AddEmployee getEmployee(@PathVariable String id) {
        return repo.findById(id).orElse(null);
    }

    // Update employee
    @PutMapping("/{id}")
    public AddEmployee updateEmployee(@PathVariable String id, @RequestBody AddEmployee emp) {
        emp.setId(id);
        return repo.save(emp);
    }

    // Delete employee
    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable String id) {
        repo.deleteById(id);
        return "Employee deleted with id " + id;
    }
}
