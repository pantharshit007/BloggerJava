package main.BlogPackage.middleware;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.Date;

public class AuthMiddleware {
    private static final String SECRET_KEY = "mySecretKey";

    // token generation
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

    // decode JWT
    public DecodedJWT verifyToken(String token) throws JWTVerificationException{
        Algorithm algo = Algorithm.HMAC256(SECRET_KEY);
        JWTVerifier verifier = JWT.require(algo)
                .withIssuer("auth0")
                .build();

        return verifier.verify(token);
    }

    // authenticate token
    public HttpHandler authenticate(HttpHandler handler){
        return exchange -> {
            String token = exchange.getRequestHeaders().getFirst("Authorization");
            if (token != null && token.startsWith("Bearer ")){
                token = token.substring(7);

                try{
                    DecodedJWT jwt = verifyToken(token);
                    String userId = jwt.getClaim("userId").asString();
                    String email = jwt.getClaim("email").asString();

                    // Add userId and email to the exchange's attributes
                    exchange.setAttribute("userId", userId);
                    exchange.setAttribute("email", email);

                    handler.handle(exchange);
                    return;

                }catch (JWTVerificationException e){
                    e.printStackTrace();
                }
            }
            unauthorizedAccess(exchange);
        };
    }

    // unauthorized access
    private void unauthorizedAccess(HttpExchange exchange) throws IOException {
        String response = "Unauthorized Access!";
        exchange.sendResponseHeaders(401, response.length());
        exchange.getResponseBody().write(response.getBytes());
        exchange.getResponseBody().close();
    }
}
