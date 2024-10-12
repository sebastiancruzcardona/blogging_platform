package com.eam.blogging_platform.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "TuClaveSecretaParaJWTDeAlMenos256BitsDeLongitud"; //signature for token

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, java.util.function.Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {//Get token signature
        byte[] keyBytes = SECRET_KEY.getBytes(); //Another option: Decoders.BASE64.decode(SECRET_KEY)
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails) { //Generate acces token
        return Jwts.builder()
                .setSubject(userDetails.getUsername()) //username
                .setIssuedAt(new Date(System.currentTimeMillis())) //token creation date in milliseconds
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours -> in milliseconds
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) //signature + encrypt algorithm (HS256 Supports hmacShaKeyFor())
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) { //Validate access token
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}