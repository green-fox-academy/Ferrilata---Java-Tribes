package com.greenfox.javatribes.javatribes.security;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

  private byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

//  @Value("${security.jwt.token.secret-key:secret-key}")
//  private String secretKey;
//
//  @Value("${security.jwt.token.expire-length:3600000}")
//  private long validityInMilliseconds = 3600000; // 1h

  @Autowired
  private MyUserDetails myUserDetails;

//  @PostConstruct
//  protected void init() {
//    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
//  }

  public String createToken(String username, List<Role> roles) {

    Claims claims = Jwts.claims().setSubject(username);
    claims.put("auth", roles.stream().map(s -> new SimpleGrantedAuthority(s.getAuthority())).filter(Objects::nonNull).collect(Collectors.toList()));



//    return Jwts.builder()//
//        .setClaims(claims)//
//        .setIssuedAt(now)//
//        .setExpiration(validity)//
//        .signWith(SignatureAlgorithm.HS512, secretKey)//
//        .compact();

    return Jwts.builder()
            .setClaims(claims)
            .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
            .setHeaderParam("type", SecurityConstants.TOKEN_TYPE)
            .setIssuer(SecurityConstants.TOKEN_ISSUER)
            .setAudience(SecurityConstants.TOKEN_AUDIENCE)
//            .setSubject(user.getUsername())
            .setExpiration(new Date(System.currentTimeMillis() + 3600000))
//            .claim("rol", "USER")
            .compact();
  }

//  private void createJWT(User user) {
//
//    byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();
//
//    this.token = Jwts.builder()
//            .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
//            .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
//            .setIssuer(SecurityConstants.TOKEN_ISSUER)
//            .setAudience(SecurityConstants.TOKEN_AUDIENCE)
//            .setSubject(user.getUsername())
//            .setExpiration(new Date(System.currentTimeMillis() + 864000000))
//            .claim("rol", "USER")
//            .compact();
//  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = myUserDetails.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getUsername(String token) {
    return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody().getSubject();
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader(SecurityConstants.TOKEN_HEADER);
    if (bearerToken != null && bearerToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      throw new CustomException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
