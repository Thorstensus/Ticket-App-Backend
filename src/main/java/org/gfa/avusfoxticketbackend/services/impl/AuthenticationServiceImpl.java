package org.gfa.avusfoxticketbackend.services.impl;

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

  private final RefreshTokenService refreshTokenServiceImpl;

  @Autowired
  public AuthenticationServiceImpl(
      UserRepository userRepository,
      JwtService jwtService,
      AuthenticationManager authManager,
      ExceptionService exceptionService,
      EmailSender emailSender,
      RefreshTokenService refreshTokenServiceImpl) {
    this.userRepository = userRepository;
    this.jwtService = jwtService;
    this.authManager = authManager;
    this.exceptionService = exceptionService;
    this.emailSender = emailSender;
    this.refreshTokenServiceImpl = refreshTokenServiceImpl;
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
    RefreshToken refreshToken = refreshTokenServiceImpl.createRefreshToken(request.getEmail());
    return new AuthenticationResponse("ok", refreshToken.getToken(), jwtToken);
  }
}
