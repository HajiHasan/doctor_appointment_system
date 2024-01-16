package az.spring.services.jwttoken;

import az.spring.dto.request.RefreshTokenDto;
import az.spring.model.Doctor;
import az.spring.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtTokenService {

    @Value("${security.jwt.private-key}")
    String privateKey;

    @Value("${security.jwt.access-token-validity-minutes}")
    long accessTokenValidityTime;

    @Value("${security.jwt.refresh-token-validity-days}")
    long refreshTokenValidityTime;

    // TODO: 1/10/2024 Here we have duplicated code we have to modify it;

    public String generateAccessToken(User user) {
        Claims claims = Jwts.claims();
        return Jwts.builder()
                 .setClaims(claims)
                 .setSubject(user.getEmail())
                 .setIssuer(user.getEmail())
                 .claim("email", user.getEmail())
                 .claim("role", user.getRole() == null ? "" : user.getRole().getName())
                 .setIssuedAt(new Date())
                 .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(accessTokenValidityTime)
                         .toInstant()))
                 .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                 .compact();
    }


    public Key getSignInKey(){
        byte [] keyBytes = Decoders.BASE64.decode(privateKey);
        return Keys.hmacShaKeyFor(keyBytes);

    }

    public Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String extractUsername(String token){
       Claims claims = extractAllClaims(token);
        return claims.get("email").toString();
    }
    public String generateRefreshToken(RefreshTokenDto refreshTokenDto){
        final User user = refreshTokenDto.getUser();
        Claims claims = Jwts.claims();
        claims.put("email", user.getEmail());
        claims.put("type", "RefreshToken");
        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setIssuedAt(new Date())
                .setExpiration(Date.from(ZonedDateTime.now()
                        .plusDays(getRefreshTokenValidityTime(refreshTokenDto.isRememberMe()))
                        .toInstant()))
                .addClaims(claims)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public Long getRefreshTokenValidityTime(boolean rememberMe){

        // if user clicks rememberMe button, our token expire time will be 30 days

        return refreshTokenValidityTime * (rememberMe ? 30L : 1L);
    }

    public <T> T extractClaim(String token, Function<Claims, T > claimsResolver) throws JwtException {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return !claims.getExpiration().before(new Date());
    }
    public boolean validateToken(String token, UserDetails userDetails){
        final String username =extractUsername(token);
        return username.equals(userDetails.getUsername()) && isTokenValid(token);
    }

    public String getJWTFromRequest(HttpServletRequest httpServletRequest){
        String bearerToken = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

}
