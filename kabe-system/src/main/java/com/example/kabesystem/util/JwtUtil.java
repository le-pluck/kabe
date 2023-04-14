package com.example.kabesystem.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.kabesystem.model.UserAccount;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;

@Component
public class JwtUtil {
    private static String SECRET;

    @Value("${jwt.secret}")
    public void setSECRET(String secret) {
        JwtUtil.SECRET = secret;
    }

    private static Long EXPIRATION;

    @Value("${jwt.expiration}")
    public void setEXPIRATION(Long expiration) {
        JwtUtil.EXPIRATION = expiration;
    }

    public static void pickValue() {
        System.out.println("SECRET" + SECRET);
        System.out.println("EXPIRATION" + EXPIRATION);
    }


    public static String issueToken(UserAccount userAccount) {
        Date issueDate = new Date();
        Date expireDate = new Date(issueDate.getTime() + EXPIRATION);

        Map<String, Object> header = new HashMap<>();
        header.put("typ", "jwt");
        header.put("alg", "HS256");

        return JWT
                .create()
                .withHeader(header)
                .withIssuedAt(issueDate)
                .withAudience(userAccount.getUsername())
                .withSubject("login")
                .withExpiresAt(expireDate)
                .withClaim("userId", userAccount.getId())
                .sign(Algorithm.HMAC256(SECRET));
    }

    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT decodedJWT;
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            decodedJWT = jwtVerifier.verify(token);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return decodedJWT.getClaims();
    }

    public static String bearerToken(String token) {
        if (token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }

}
