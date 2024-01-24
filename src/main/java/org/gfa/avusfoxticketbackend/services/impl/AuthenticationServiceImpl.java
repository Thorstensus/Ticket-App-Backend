package org.gfa.avusfoxticketbackend.services.impl;

import java.util.Map;
import org.gfa.avusfoxticketbackend.config.JwtService;
import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDTO;
import org.gfa.avusfoxticketbackend.dtos.authdtos.AuthenticationRequest;
import org.gfa.avusfoxticketbackend.dtos.authdtos.AuthenticationResponse;
import org.gfa.avusfoxticketbackend.email.EmailSender;
import org.gfa.avusfoxticketbackend.enums.Role;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.repositories.CustomUserRepository;
import org.gfa.avusfoxticketbackend.services.AuthenticationService;
import org.gfa.avusfoxticketbackend.services.ExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

  private final CustomUserRepository customUserRepository;
  private final JwtService jwtService;
  private final AuthenticationManager authManager;
  private final ExceptionService exceptionService;
  private final EmailSender emailSender;

  @Autowired
  public AuthenticationServiceImpl(
      CustomUserRepository customUserRepository,
      JwtService jwtService,
      AuthenticationManager authManager,
      ExceptionService exceptionService,
      EmailSender emailSender) {
    this.customUserRepository = customUserRepository;
    this.jwtService = jwtService;
    this.authManager = authManager;
    this.exceptionService = exceptionService;
    this.emailSender = emailSender;
  }

  @Override
  public ResponseDTO authenticate(AuthenticationRequest request) {
    exceptionService.checkForUserErrors(request);
    User authenticatedUser = customUserRepository.findByEmail(request.getEmail()).get();
    authManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    String jwtToken =
        jwtService.generateToken(
            Map.of(
                "userId", authenticatedUser.getId(),
                "isAdmin", authenticatedUser.getRole() == Role.ADMIN,
                "isVerified", authenticatedUser.isVerified()),
            authenticatedUser);
    return new AuthenticationResponse("ok", jwtToken);
  }
}
