package az.spring.services.jwt;

import az.spring.model.User;
import az.spring.services.base.TokenGenerator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtAccessToken implements TokenGenerator<User>{

    @Value("${security.jwt.private-key}")
    String privateKey;

    @Value("${security.jwt.access-token-validity-minutes}")
    long accessTokenValidityTime;

    @Override
    public String generateToken(User obj) {

        Claims claims = Jwts.claims();
        claims.put("email", obj.getEmail());

         return Jwts.builder()
                .setSubject(String.valueOf(obj.getId()))
                .setIssuedAt(new Date())
                .setExpiration(Date.from(ZonedDateTime.now()
                        .plusMinutes(accessTokenValidityTime).toInstant()))
                 .addClaims(claims)
                //.setClaims(claims)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Key getSignInKey(){
        byte [] keyBytes = Decoders.BASE64.decode(privateKey);
        return Keys.hmacShaKeyFor(keyBytes);

    }

    public Claims read(String token) {

        //this "read" method replaces extractAllClaims method, which used in the last projects!!!!!!

        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
