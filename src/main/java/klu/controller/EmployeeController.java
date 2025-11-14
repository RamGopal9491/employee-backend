package klu.controller;

import klu.model.Employee;
import klu.model.Users;
import klu.repository.EmployeeRepository;
import klu.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")  // allow frontend access
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

 

    // ✅ Get employee by id
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> emp = employeeRepository.findById(id);
        return emp.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Add new employee
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    // ✅ Update employee
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        return employeeRepository.findById(id).map(employee -> {
            employee.setName(employeeDetails.getName());
            employee.setDob(employeeDetails.getDob());
            employee.setDepartment(employeeDetails.getDepartment());
            employee.setGender(employeeDetails.getGender());
            employee.setMaritalStatus(employeeDetails.getMaritalStatus());
            employee.setDesignation(employeeDetails.getDesignation());
            employee.setSalary(employeeDetails.getSalary());
            return ResponseEntity.ok(employeeRepository.save(employee));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        return employeeRepository.findById(id).map(employee -> {
            employeeRepository.delete(employee);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
