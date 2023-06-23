package com.hackathon.apartxhackathon.controller;

import com.hackathon.apartxhackathon.exception.IncorrectVerificationCodeException;
import com.hackathon.apartxhackathon.exception.UserAlreadyExistsException;
import com.hackathon.apartxhackathon.exception.UserNotFoundException;
import com.hackathon.apartxhackathon.request.AuthenticationRequest;
import com.hackathon.apartxhackathon.request.RegisterRequest;
import com.hackathon.apartxhackathon.request.VerifyEmailRequest;
import com.hackathon.apartxhackathon.response.AuthenticationResponse;
import com.hackathon.apartxhackathon.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hackathon.apartxhackathon.service.AuthenticationService;


import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<User> register(
      @RequestBody RegisterRequest request
  ) throws UserAlreadyExistsException {
    return ResponseEntity.ok(service.register(request));
  }

  @PostMapping("/verify")
  public ResponseEntity<AuthenticationResponse> verify(
          @RequestBody VerifyEmailRequest request
  ) throws IncorrectVerificationCodeException, UserNotFoundException {
    return ResponseEntity.ok(service.verify(request));
  }
  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    service.refreshToken(request, response);
  }


}
