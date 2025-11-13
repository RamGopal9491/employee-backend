package klu.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtManager {
    private static final String SEC_KEY = "ASDFGHJKLQWERTYUIOPZXCVBNM098762345612abdgceftyuobght";
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SEC_KEY.getBytes());

    // Generate JWT
    public String generateJWT(String fullname) {
        Map<String, Object> data = new HashMap<>();
        data.put("fullname", fullname);
        

        return Jwts.builder()
                .setClaims(data)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(KEY)
                .compact();
    }

    // Validate JWT Token
    public String validateToken(String token) {
        try {
            Claims claim = Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Date expiry = claim.getExpiration();
            if (expiry == null || expiry.before(new Date())) {
                return "401"; // expired
            }

            // retrieve the correct claim
            return claim.get("fullname", String.class);

        } catch (Exception e) {
            return "401"; // invalid token
        }
    }
}
