package com.example.airbnb.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.airbnb.exception.ErrorMessage;
import com.example.airbnb.exception.TokenException;
import com.fasterxml.jackson.databind.node.TextNode;

import java.util.Date;

public class JwtUtil {

    private static final String SECRET = "secret";
    private static final String CLAIM_KEY = "userId";
    private static final Algorithm algorithmHS = Algorithm.HMAC256(SECRET);

    public static String createToken(String userId) {
        return JWT.create()
                .withClaim(CLAIM_KEY, userId)
                .withExpiresAt(new Date())
                .sign(algorithmHS);
    }

    public static String getUserIdFromToken(String token) {
        DecodedJWT decodedJWT = verifyToken(token);
        return decodedJWT.getClaims().get(CLAIM_KEY).as(TextNode.class).asText();
    }

    private static DecodedJWT verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithmHS)
                    .acceptExpiresAt(600*2400*600)
                    .build();
            return verifier.verify(token);
        } catch (JWTDecodeException | TokenExpiredException e) {
            throw new TokenException(ErrorMessage.INVALID_TOKEN);
        }
    }

    public static String getTokenFromAuthorization(String authorization) {
        String[] authArray = authorization.split(" ");
        if (authArray.length < 2 || !authArray[0].equals("Bearer")) {
            throw new TokenException(ErrorMessage.INVALID_TOKEN);
        }
        return authArray[1];
    }
}
