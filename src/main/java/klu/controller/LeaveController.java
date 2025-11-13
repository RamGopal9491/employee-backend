package klu.controller;

import klu.model.Leave;
import klu.model.Users;
import klu.repository.LeaveRepository;
import klu.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dto.LeaveRequestDTO;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/leaves")
@CrossOrigin(origins = "http://localhost:5173")
public class LeaveController {

    @Autowired
    private LeaveRepository leaveRepo;
    
    

    @Autowired
    private UserRepository userRepo;

    // ✅ Apply new leave
    @PostMapping
    public ResponseEntity<?> applyLeave(@RequestBody LeaveRequestDTO dto) {
        try {
            Users user = userRepo.findById(dto.getEmpId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));

            Leave leave = new Leave();
            leave.setUser(user);
            leave.setType(dto.getType());
            leave.setReason(dto.getReason());
            leave.setStartDate(dto.getStartDate());
            leave.setEndDate(dto.getEndDate());
            leave.setDays(dto.getDays());
            leave.setStatus("Pending");

            Leave saved = leaveRepo.save(leave);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // ✅ Get leaves by employee ID
    @GetMapping("/emp/{empId}")
    public ResponseEntity<?> getLeavesByEmployee(@PathVariable Long empId) {
        List<Leave> leaves = leaveRepo.findByUserEmpid(empId);
        return ResponseEntity.ok(leaves);
    }

    // ✅ Approve leave
    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approveLeave(@PathVariable UUID id) {
        Leave leave = leaveRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found"));
        leave.setStatus("Approved");
        return ResponseEntity.ok(leaveRepo.save(leave));
    }

    // ✅ Reject leave
    @PutMapping("/{id}/reject")
    public ResponseEntity<?> rejectLeave(@PathVariable UUID id) {
        Leave leave = leaveRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found"));
        leave.setStatus("Rejected");
        return ResponseEntity.ok(leaveRepo.save(leave));
    }

    // ✅ Get all leaves
    @GetMapping
    public ResponseEntity<?> getAllLeaves() {
        try {
            List<Leave> leaves = leaveRepo.findAll();
            return ResponseEntity.ok(leaves);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("message", "Error fetching leaves"));
        }
    }
    
    @GetMapping("/applied/{date}")
    public ResponseEntity<?> getLeavesByAppliedDate(@PathVariable String date) {
        LocalDate appliedDate = LocalDate.parse(date);
        List<Leave> leaves = leaveRepo.findByAppliedDate(appliedDate);
        return ResponseEntity.ok(Map.of(
            "count", leaves.size(),
            "leaves", leaves
        ));
    }

}
