package dto;

import org.springframework.stereotype.Component;

import klu.model.Users;

@Component
public class UserResponse {
    private String token;
    private Users user;

    // ✅ Constructors
    public UserResponse() {}

    public UserResponse(String token, Users user) {
        this.token = token;
        this.user = user;
    }

    // ✅ Getters & Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public Users getUser() { return user; }
    public void setUser(Users user) { this.user = user; }
}
