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
        if (UR.countByEmail(U.getEmail()) > 0) {
            return new ResponseEntity<>("401::Email Already Exist", HttpStatus.NOT_FOUND);
        } else {
            System.out.println("signup manager");
            System.out.println(U.getFullname());
            UR.save(U);
            return new ResponseEntity<>("200::Registration Successful", HttpStatus.OK);
        }
    }

    // --- Password reset by empid ---
    public String getPassword(Long empid) {
        Users U = UR.findById(empid).orElse(null);
        if (U == null) {
            return null;
        }
        String message = "Dear " + U.getFullname() +
                         "\n\nYour password is " + U.getPassword();
        return EM.sendEmail(U.getEmail(), "EMPLOYEE: Password Recovery", message);
    }

    // --- Sign In ---
    public UserResponse signIn(String email, String password) {
        System.out.println("signin");
        System.out.println(email + " " + password);

        // validate credentials (your existing count method)
        if (UR.validateCredentials(email, password) == 0) {
            System.out.println("wrong credentials");
            return null; // invalid credentials
        }

        String token = JWT.generateJWT(email);
        System.out.println(token);

        // fetch user safely using Optional
        return UR.findByEmail(email)
                 .map(user -> new UserResponse(token, user))
                 .orElse(null);
    }
}
