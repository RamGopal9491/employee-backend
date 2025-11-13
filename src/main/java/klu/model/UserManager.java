package klu.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import dto.UserResponse;
import klu.repository.UserRepository;

@Service
public class UserManager {

    @Autowired
    private UserRepository UR;

    @Autowired
    private EmailManager EM;

    @Autowired
    private JwtManager JWT;

    // --- Add User ---
    public ResponseEntity<String> addUser(Users U) {
        try {
            if (UR.validateEmail(U.getEmail()) > 0) {
                return new ResponseEntity<>("401::Email Already Exist", HttpStatus.BAD_REQUEST);
            } else {
                System.out.println("Signup Manager: " + U.getFullname());
                UR.save(U);
                return new ResponseEntity<>("200::Registration Successful", HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("500::Internal Server Error: " + e.getMessage(),
                                        HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // --- Password reset by empid ---
    public String getPassword(Long empid) {
        Users U = UR.findById(empid).orElse(null);
        if (U == null) {
            return "Employee not found";
        }

        String message = "Dear " + U.getFullname() +
                         ",\n\nYour password is: " + U.getPassword();
        return EM.sendEmail(U.getEmail(), "EMPLOYEE: Password Recovery", message);
    }

    // --- Sign In ---
    public UserResponse signIn(String email, String password) {
        System.out.println("Signin attempt: " + email);

        // Check credentials
        if (UR.validateCredentials(email, password) == 0) {
            System.out.println("❌ Invalid credentials for " + email);
            return null;
        }

        // Generate JWT token
        String token = JWT.generateJWT(email);

        // Fetch user info
        Users user = UR.findByEmail(email);
        if (user == null) {
            return null;
        }

        // Return response with token + user info
        return new UserResponse(token, user);
    }
}
