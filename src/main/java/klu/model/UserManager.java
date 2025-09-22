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
    UserRepository UR;

    @Autowired
    EmailManager EM;

    @Autowired
    JwtManager JWT;

    // --- Add User ---
    public ResponseEntity<String> addUser(Users U) {
        if (UR.validateEmail(U.getEmail()) > 0) {
            return new ResponseEntity<>("401::Email Already Exist", HttpStatus.NOT_FOUND);
        } else {
        	System.out.println("signup manger");
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
    public UserResponse signIn(String email, String password, String role) {
    	System.out.println("signim");
    	System.out.println(email+""+password+""+role);
        if (UR.validateCredentials(email, password, role) == 0) {
        	System.out.println("wromg ");
            return null; // invalid credentials
        }
        
        String token = JWT.generateJWT(email, role);
        System.out.println(token);
        Users user = UR.findByEmail(email); // fetch user from repo
        return new UserResponse(token, user);
    }

}
