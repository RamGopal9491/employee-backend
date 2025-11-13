package klu.controller;

import klu.model.Payroll;
import klu.repository.PayrollRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/payrolls")
@CrossOrigin(origins = "http://localhost:5173")   // React frontend URL
public class PayrollController {

    private final PayrollRepository payrollRepository;

    public PayrollController(PayrollRepository payrollRepository) {
        this.payrollRepository = payrollRepository;
    }

    // ✅ Get all payrolls (Admin)
    @GetMapping
    public List<Payroll> getAllPayrolls() {
        return payrollRepository.findAll();	
    }

    // ✅ Get payroll(s) by employee ID (Employee)
    @GetMapping("/emp/id/{empid}")
    public List<Payroll> getPayrollByEmpId(@PathVariable Long empid) {
    	System.out.println("hmj");
        return payrollRepository.findByEmpid(empid);
    }

    // ✅ Create payroll
    @PostMapping
    public Payroll createPayroll(@RequestBody Payroll payroll) {
        payroll.setId(null); // new ID auto-generated
        return payrollRepository.save(payroll);
    }

    // ✅ Update salary
    @PutMapping("/{id}/salary")
    public Payroll updateSalary(@PathVariable Long id, @RequestBody Payroll payroll) {
        Payroll existing = payrollRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payroll not found with id " + id));
        existing.setSalary(payroll.getSalary());
        return payrollRepository.save(existing);
    }
}
