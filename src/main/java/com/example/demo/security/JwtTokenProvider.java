// package com.example.demo.security;

// import com.example.demo.model.AppUser;
// import io.jsonwebtoken.*;
// import io.jsonwebtoken.security.Keys;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;

// import java.security.Key;
// import java.util.Date;

// @Component
// public class JwtTokenProvider {

//     private final Key key;
//     private final long validityInMs;

//     public JwtTokenProvider(@Value("${app.jwt.secret}") String secret,
//                             @Value("${app.jwt.expiration-ms}") long validityInMs) {
//         this.key = Keys.hmacShaKeyFor(secret.getBytes());
//         this.validityInMs = validityInMs;
//     }

//     public String generateToken(AppUser user) {
//         Date now = new Date();
//         Date expiry = new Date(now.getTime() + validityInMs);

//         return Jwts.builder()
//                 .setSubject(user.getEmail())
//                 .claim("role", user.getRole())
//                 .claim("userId", user.getId())
//                 .setIssuedAt(now)
//                 .setExpiration(expiry)
//                 .signWith(key, SignatureAlgorithm.HS256)
//                 .compact();
//     }

//     public boolean validateToken(String token) {
//         try {
//             Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
//             return true;
//         } catch (JwtException e) {
//             return false;
//         }
//     }

//     public String getEmailFromToken(String token) {
//         return Jwts.parserBuilder().setSigningKey(key).build()
//                 .parseClaimsJws(token).getBody().getSubject();
//     }
// }


// package com.example.demo.security;

// import com.example.demo.model.AppUser;
// import org.springframework.stereotype.Component;

// @Component
// public class JwtTokenProvider {
//     // Logic not strictly required for unit tests as it is mocked
//     public String generateToken(AppUser user) {
//         return "mock-token";
//     }
// }

package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    // Generate a secure key for HS512
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final long jwtExpirationInMs = 3600000; // 1 hour

    public String generateToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername()) // usually email
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            // Log exception
        }
        return false;
    }
}