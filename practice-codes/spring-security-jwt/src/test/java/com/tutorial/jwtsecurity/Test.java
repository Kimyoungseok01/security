package com.tutorial.jwtsecurity;

import com.tutorial.jwtsecurity.jwt.TokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import org.junit.jupiter.api.DisplayName;
import org.springframework.security.core.Authentication;

public class Test {
  private String key;

  @org.junit.jupiter.api.Test
  @DisplayName("getAuthentication")
  void test(){
    key = "c3ByaW5nLWJvb3Qtc2VjdXJpdHktand0LXR1dG9yaWFsLWppd29vbi1zcHJpbmctYm9vdC1zZWN1cml0eS1qd3QtdHV0b3JpYWwK";
    TokenProvider tokenProvider = new TokenProvider("c3ByaW5nLWJvb3Qtc2VjdXJpdHktand0LXR1dG9yaWFsLWppd29vbi1zcHJpbmctYm9vdC1zZWN1cml0eS1qd3QtdHV0b3JpYWwK");
    Authentication authentication = tokenProvider.getAuthentication("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyIiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTY2NTU1NzQ3M30.3qN-a8CZGB3L0Ns0-cMAOMBU_01jLl27FSJO69IZ07cRXNSHkEPKtHbCeSSKXpbWvKe8nDMnzsZGplT2IKsDbQ");
    Claims claims = parseClaims("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyIiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTY2NTU1NzQ3M30.3qN-a8CZGB3L0Ns0-cMAOMBU_01jLl27FSJO69IZ07cRXNSHkEPKtHbCeSSKXpbWvKe8nDMnzsZGplT2IKsDbQ");
    System.out.println("claims = " + claims);
    System.out.println("authentication = " + authentication);
  }

  private Claims parseClaims(String accessToken) {
    try {
      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }
}
