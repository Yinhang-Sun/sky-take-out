package com.sky.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    /**
     * Generate jwt
     * Use Hs256 algorithm, use fixed secret key for private key
     *
     * @param secretKey jwt secret key
     * @param ttlMillis jwt expiration time (milliseconds)
     * @param claims information set
     * @return
     */
    public static String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {
        // Specify the signature algorithm used when signing, that is, the header part
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //Time to generate JWT
        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(expMillis);

        //Set the body of jwt
        JwtBuilder builder = Jwts.builder()
                // If there is a private statement, you must first set the private statement you created.
                // This is to assign a value to the builder's claim. Once it is written after the standard statement assignment,
                // it will overwrite those standard statements.
                .setClaims(claims)
                //Set the signature algorithm used for signature and the secret key used for signature
                .signWith(signatureAlgorithm, secretKey.getBytes(StandardCharsets.UTF_8))
                //Set expiration time
                .setExpiration(exp);

        return builder.compact();
    }

    /**
     * Token decryption
     *
     * @param secretKey jwt secret key This secret key must be kept on the server and cannot be exposed,
     *                  otherwise the sign can be forged. If you connect to multiple clients, it is recommended to change it to multiple
     * @param token encrypted token
     * @return
     */
    public static Claims parseJWT(String secretKey, String token) {

        // Get DefaultJwtParser
        Claims claims = Jwts.parser()
                //Set the signature key
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                //Set the jwt that needs to be parsed
                .parseClaimsJws(token).getBody();
        return claims;
    }

}
