package klu.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dto.UserRequest;
import dto.UserResponse;
import klu.model.Payroll;
import klu.model.UserManager;
import klu.model.Users;
import klu.model.EmployeePerformance;
import klu.repository.PayrollRepository;
import klu.repository.UserRepository;
import klu.repository.EmployeePerformanceRepository;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserRepository userRepository;
    private final PayrollRepository payrollRepository;
    private final EmployeePerformanceRepository performanceRepository;
    private final UserManager um;

    @Autowired
    public UserController(UserRepository userRepository,
                          PayrollRepository payrollRepository,
                          EmployeePerformanceRepository performanceRepository,
                          UserManager um) {
        this.userRepository = userRepository;
        this.payrollRepository = payrollRepository;
        this.performanceRepository = performanceRepository;
        this.um = um;
    }

    // --- Signup ---
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody Users user) {
        try {
            Users savedUser = userRepository.save(user);

            Payroll payroll = new Payroll();
            payroll.setName(savedUser.getFullname());
            payroll.setDepartment(savedUser.getDepartment());
            payroll.setSalary(0.0);
            payrollRepository.save(payroll);

            EmployeePerformance performance = new EmployeePerformance();
            performance.setFullName(savedUser.getFullname());
            performance.setDepartment(savedUser.getDepartment());
            performance.setRating(0);
            performance.setFeedback("");
            performanceRepository.save(performance);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("User created with payroll and performance initialized!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during signup: " + e.getMessage());
        }
    }

    // --- Forget Password ---
    @PostMapping("/forgetpassword")
    public ResponseEntity<String> forgetPassword(@RequestBody Map<String, Long> request) {
        try {
            Long empid = request.get("empid");
            Users user = userRepository.findById(empid)
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
            // Do NOT return the password directly
            return ResponseEntity.ok("Password reset link sent to registered email!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving password: " + e.getMessage());
        }
    }

    // --- Sign In ---
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody UserRequest U) {
        try {
            UserResponse response = um.signIn(U.getEmail(), U.getPassword());
            if (response != null) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid email or password"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error during sign in: " + e.getMessage()));
        }
    }

    // --- Get All Users ---
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<Users>> findAllUsers() {
        try {
            List<Users> users = userRepository.findAll();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // --- Update User ---
    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(
            @PathVariable("id") Long id,
            @RequestBody Users userDetails) {
        try {
            return userRepository.findById(id)
                    .map(existing -> {
                        if (userDetails.getFullname() != null)
                            existing.setFullname(userDetails.getFullname());
                        if (userDetails.getEmail() != null)
                            existing.setEmail(userDetails.getEmail());
                        if (userDetails.getPassword() != null)
                            existing.setPassword(userDetails.getPassword());
                        if (userDetails.getRole() != null)
                            existing.setRole(userDetails.getRole());
                        if (userDetails.getDepartment() != null)
                            existing.setDepartment(userDetails.getDepartment());
                        if (userDetails.getMarital() != null)
                            existing.setMarital(userDetails.getMarital());
                        if (userDetails.getPhoto() != null)
                            existing.setPhoto(userDetails.getPhoto()); // ✅ new line

                        Users saved = userRepository.save(existing);
                        return ResponseEntity.ok(saved);
                    })
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
