package klu.controller;

import klu.model.Leave;
import klu.model.Users;
import klu.repository.LeaveRepository;
import klu.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import dto.LeaveRequestDTO;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/leaves")
@CrossOrigin(origins = "*") // React frontend
public class LeaveController {

    @Autowired
    private LeaveRepository leaveRepo;

    @Autowired
    private UserRepository userRepo;

    // ✅ Apply new leave (accept DTO with empId)
    @PostMapping
    public Leave applyLeave(@RequestBody LeaveRequestDTO dto) {
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

        return leaveRepo.save(leave);
    }

    // ✅ Get leaves by employee ID
    @GetMapping("/emp/{empId}")
    public List<Leave> getLeavesByEmployee(@PathVariable Long empId) {
        return leaveRepo.findByUserEmpid(empId);
    }

    // ✅ Approve leave
    @PutMapping("/{id}/approve")
    public Leave approveLeave(@PathVariable UUID id) {
        Leave leave = leaveRepo.findById(id).orElseThrow();
        leave.setStatus("Approved");
        return leaveRepo.save(leave);
    }

    // ✅ Reject leave
    @PutMapping("/{id}/reject")
    public Leave rejectLeave(@PathVariable UUID id) {
        Leave leave = leaveRepo.findById(id).orElseThrow();
        leave.setStatus("Rejected");
        return leaveRepo.save(leave);
    }
}
	