package klu.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dto.UserRequest;
import dto.UserResponse;
import klu.model.UserManager;
import klu.model.Users;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserManager um;

    // --- Signup ---
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody Users U) {
        try {
        	System.out.println(U.getFullname());
            return um.addUser(U); // already returns ResponseEntity
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error during signup: " + e.getMessage());
        }
    }

    // --- Forget Password (by empid) ---
    @PostMapping("/forgetpassword")
    public ResponseEntity<String> forgetPassword(@RequestBody Map<String, Long> request) {
        try {
            Long empid = request.get("empid");
            String password = um.getPassword(empid);

            if (password != null) {
                return ResponseEntity.ok(password);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("Employee not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error retrieving password: " + e.getMessage());
        }
    }

    // --- Sign In ---
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody UserRequest U) {
        try {
            UserResponse response = um.signIn(U.getEmail(), U.getPassword(), U.getRole());
            
            if (response != null) {
                return ResponseEntity.ok(response); // Automatically returns token + user object
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                     .body(Map.of("error", "Invalid email or password"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Error during sign in: " + e.getMessage()));
        }
    }

}
