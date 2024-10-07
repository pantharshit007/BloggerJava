package main.BlogPackage.middleware;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class AuthMiddleware {
    private static final String SECRET_KEY = "mySecretKey";

    public String generateToken(String userId, String email){
        Algorithm algo = Algorithm.HMAC256(SECRET_KEY);
        return JWT.create()
                .withIssuer("auth0")
                .withClaim("userId",userId)
                .withClaim("email",email)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000)) // 24 hours expiry
                .sign(algo);
    }

    // authenticate token
    public boolean isAuthenticated(String token){
        try{
            Algorithm algo = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algo)
                    .withIssuer("auth0")
                    .build();

            DecodedJWT jwt = verifier.verify(token);

            String userId = jwt.getClaim("userId").asString();
            String email = jwt.getClaim("email").asString();

            return true;
        }catch (JWTVerificationException exception){
            exception.printStackTrace();
            return false;
        }
    }
}
