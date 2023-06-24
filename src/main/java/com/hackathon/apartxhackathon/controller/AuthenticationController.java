package com.hackathon.apartxhackathon.controller;

import com.hackathon.apartxhackathon.exception.IncorrectVerificationCodeException;
import com.hackathon.apartxhackathon.exception.UserAlreadyExistsException;
import com.hackathon.apartxhackathon.exception.UserNotFoundException;
import com.hackathon.apartxhackathon.request.AuthenticationRequest;
import com.hackathon.apartxhackathon.request.RegisterRequest;
import com.hackathon.apartxhackathon.request.VerifyEmailRequest;
import com.hackathon.apartxhackathon.response.AuthenticationResponse;
import com.hackathon.apartxhackathon.response.UserInfoResponse;
import com.hackathon.apartxhackathon.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.hackathon.apartxhackathon.service.AuthenticationService;


import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity register(
      @RequestBody RegisterRequest request
  ) throws UserAlreadyExistsException {
    service.register(request);
    return ResponseEntity.ok().build();
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

  @GetMapping("/getinfo")
  public ResponseEntity<UserInfoResponse> getInfo(
          @AuthenticationPrincipal UserDetails userDetails
          ) throws UserNotFoundException {
    return ResponseEntity.ok(service.getInfo(userDetails));
  }

}
