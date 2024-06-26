package com.petopia.petopia.services_implementors;

import com.petopia.petopia.enums.Const;
import com.petopia.petopia.models.entity_models.Token;
import com.petopia.petopia.repositories.TokenRepo;
import com.petopia.petopia.services.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTService {

    @Value("f5aa9f96034f1b8baab27eab35a7b76cf7120f8330be3e257d6104fc9ba4ce0e")
    private String secretKey;

    @Value("1209600000") // 2 weeks
    private long accessTokenExpiration;

    @Value("12096000000") // 5 months
    private long refreshTokenExpiration;

    private final TokenRepo tokenRepo;

    @Override
    public String extractEmail(String token) {
        return extractRequiredClaim(token, Claims::getSubject);
    }

    @Override
    public String generateAccessToken(UserDetails user) {
        return generateToken(new HashMap<>(), user, accessTokenExpiration);
    }

    @Override
    public String generateRefreshToken(UserDetails user) {
        return generateToken(new HashMap<>(), user, refreshTokenExpiration);
    }

    @Override
    public boolean checkTokenIsValid(String token) {
        Token t = tokenRepo.findByValue(token).orElse(null);
        if(t == null) return false;
        if(t.getTokenStatus().getStatus().equals(Const.TOKEN_STATUS_EXPIRED)) return false;
        return !extractRequiredClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extractRequiredClaim(String token, Function<Claims, T> claimsResolver){
        return claimsResolver.apply(extractAllClaims(token));
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, long expirationTime){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey())
                .compact();
    }
}
