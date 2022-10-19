package com.tutorial.jwtsecurity;

import com.tutorial.jwtsecurity.jwt.TokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Test {
  private String key;


  private Claims parseClaims(String accessToken) {
    try {
      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }

  @org.junit.jupiter.api.Test
   void test1(){
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    System.out.println("authentication.getName() = " + authentication.getName());
    System.out.println("authentication.getAuthorities() = " + authentication.getAuthorities());
    System.out.println("authentication.getCredentials() = " + authentication.getCredentials());
    System.out.println("authentication.getDetails() = " + authentication.getDetails());
  }
}
