package com.example.demo.security;


import io.jsonwebtoken.*;
import org.apache.juli.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);
    @Value("${app.jwtSecret}")
    private String jwtSecret;
//    @Value("@{app.jwtExp}")
    private String jwtExp;
    public String generateToken(Authentication authentication){
        UsePrincipal usePrincipal = (UsePrincipal) authentication.getPrincipal();
        Date now = new Date();
//        Integer jwtExpDate = Integer.parseInt(String.valueOf(jwtExp));
        Date expDate = new Date(now.getTime() + 604800000);
        return Jwts.builder()
                .setSubject(Long.toString(usePrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expDate)
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
    }
    public Long getUserIdFromJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }
    public boolean validateToken(String authToken){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJwt(authToken);
            return true;
        }catch (SignatureException e){
            LOGGER.error("invalid JWT signature");
        }catch (MalformedJwtException malformedJwtException){
            LOGGER.error("Invalid JWT token");
        }catch (ExpiredJwtException expiredJwtException){
            LOGGER.error("Exp JWT token");
        }catch (UnsupportedJwtException unsupportedJwtException){
            LOGGER.error("Unsupport JWT token");
        }catch (IllegalArgumentException e){
            LOGGER.error("JWT claims string is empty");
        }
        return false;
    }
}
