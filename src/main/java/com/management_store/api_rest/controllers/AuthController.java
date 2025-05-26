package com.management_store.api_rest.controllers;

import com.management_store.api_rest.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
  @Autowired
  private AuthenticationManager authManager;
  @Autowired
  private JwtService jwtService;

  @PostMapping("/login")
  public TokenResponse login(@RequestBody LoginRequest request) {
    Authentication auth = authManager.authenticate(
      new UsernamePasswordAuthenticationToken(request.email(), request.password()));

    UserPrincipal user = (UserPrincipal) auth.getPrincipal();
    String token = jwtService.generateToken(user);
    return new TokenResponse(token);
  }

  public record LoginRequest(String email, String password) {
  }

  public record TokenResponse(String token) {
  }
}
