package org.gfa.avusfoxticketbackend.services.impl;

import java.util.Date;
import java.util.Map;
import org.gfa.avusfoxticketbackend.config.models.RefreshToken;
import org.gfa.avusfoxticketbackend.config.services.JwtService;
import org.gfa.avusfoxticketbackend.config.services.RefreshTokenService;
import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDTO;
import org.gfa.avusfoxticketbackend.dtos.authdtos.AuthenticationRequest;
import org.gfa.avusfoxticketbackend.dtos.authdtos.AuthenticationResponse;
import org.gfa.avusfoxticketbackend.email.EmailSender;
import org.gfa.avusfoxticketbackend.enums.Role;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.repositories.UserRepository;
import org.gfa.avusfoxticketbackend.services.AuthenticationService;
import org.gfa.avusfoxticketbackend.services.ExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final AuthenticationManager authManager;
  private final ExceptionService exceptionService;
  private final EmailSender emailSender;

  private final RefreshTokenService refreshTokenService;

  @Autowired
  public AuthenticationServiceImpl(
      UserRepository userRepository,
      JwtService jwtService,
      AuthenticationManager authManager,
      ExceptionService exceptionService,
      EmailSender emailSender,
      RefreshTokenService refreshTokenService) {
    this.userRepository = userRepository;
    this.jwtService = jwtService;
    this.authManager = authManager;
    this.exceptionService = exceptionService;
    this.emailSender = emailSender;
    this.refreshTokenService = refreshTokenService;
  }

  @Override
  public ResponseDTO authenticate(AuthenticationRequest request) {
    exceptionService.checkForUserErrors(request);
    User authenticatedUser = userRepository.findByEmail(request.getEmail()).get();
    authManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    String jwtToken =
        jwtService.generateToken(
            Map.of(
                "userId", authenticatedUser.getId(),
                "isAdmin", authenticatedUser.getRole() == Role.ADMIN,
                "isVerified", authenticatedUser.isVerified()),
            authenticatedUser);
    RefreshToken refreshToken;
    if (authenticatedUser.getRefreshToken() == null) {
      refreshToken = refreshTokenService.createRefreshToken(request.getEmail());
    } else {
      refreshToken = authenticatedUser.getRefreshToken();
      if (refreshToken.getExpiryDate().before(new Date())) {
        refreshToken.setExpiryDate(new Date(System.currentTimeMillis() + refreshTokenService.getExpirationTime()));
        refreshTokenService.saveRefreshToken(refreshToken);
      }
    }
    return new AuthenticationResponse("ok", refreshToken.getToken(), jwtToken);
  }
}
