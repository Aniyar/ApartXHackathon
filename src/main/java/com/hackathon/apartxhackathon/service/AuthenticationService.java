package com.hackathon.apartxhackathon.service;

import com.hackathon.apartxhackathon.config.JwtService;
import com.hackathon.apartxhackathon.exception.IncorrectVerificationCodeException;
import com.hackathon.apartxhackathon.exception.UserAlreadyExistsException;
import com.hackathon.apartxhackathon.exception.UserNotFoundException;
import com.hackathon.apartxhackathon.model.Cleaner;
import com.hackathon.apartxhackathon.model.LandLord;
import com.hackathon.apartxhackathon.repository.CleanerRepository;
import com.hackathon.apartxhackathon.repository.LandLordRepository;
import com.hackathon.apartxhackathon.request.AuthenticationRequest;
import com.hackathon.apartxhackathon.request.RegisterRequest;
import com.hackathon.apartxhackathon.request.VerifyEmailRequest;
import com.hackathon.apartxhackathon.response.AuthenticationResponse;
import com.hackathon.apartxhackathon.token.Token;
import com.hackathon.apartxhackathon.token.TokenRepository;
import com.hackathon.apartxhackathon.token.TokenType;
import com.hackathon.apartxhackathon.user.Role;
import com.hackathon.apartxhackathon.user.User;
import com.hackathon.apartxhackathon.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
    private final LandLordRepository llRepository;
    private final CleanerRepository cleanerRepository;

  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final EmailService emailService;

  public void register(RegisterRequest request) throws UserAlreadyExistsException {
    if (repository.findByEmail(request.getEmail()).isPresent()){
      throw new UserAlreadyExistsException();
    }
    var user = User.builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .iin(request.getIin())
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .role(request.getRole())
            .code(generateRandomCode())
            .approved(false)
            .build();
    emailService.sendAuthorizationCode(user.getEmail(), user.getCode());
    repository.save(user);

  }

  public AuthenticationResponse verify(VerifyEmailRequest request) throws UserNotFoundException, IncorrectVerificationCodeException {
    User user = repository.findByEmail(request.getEmail()).orElseThrow(UserNotFoundException::new);
    if (!request.getCode().equals(user.getCode())) {
      throw new IncorrectVerificationCodeException();
    }
    user.setApproved(true);
    repository.save(user);

      if (user.getRole() == Role.CLEANER){
          Cleaner cleaner = Cleaner.builder()
                  .user(user)
                  .build();
          cleanerRepository.save(cleaner);
      }

      if (user.getRole() == Role.LANDLORD){
          LandLord landLord = LandLord.builder()
                  .user(user)
                  .build();
          llRepository.save(landLord);
      }
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    saveUserToken(user, jwtToken);
    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = repository.findByEmail(request.getEmail())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
        .build();
  }

  public static String generateRandomCode() {
    SecureRandom secureRandom = new SecureRandom();
    StringBuilder code = new StringBuilder();

    for (int i = 0; i < 4; i++) {
      int digit = secureRandom.nextInt(10); // Generate a random digit between 0 and 9
      code.append(digit);
    }

    return code.toString();
  }

  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.repository.findByEmail(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }


}
