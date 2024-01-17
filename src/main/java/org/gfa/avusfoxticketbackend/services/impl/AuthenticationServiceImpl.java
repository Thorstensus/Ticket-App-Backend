package org.gfa.avusfoxticketbackend.services.impl;

import java.util.Map;
import org.gfa.avusfoxticketbackend.config.JwtServiceImpl;
import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDTO;
import org.gfa.avusfoxticketbackend.dtos.authdtos.AuthenticationRequest;
import org.gfa.avusfoxticketbackend.dtos.authdtos.AuthenticationResponse;
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
  private final JwtServiceImpl jwtServiceImpl;
  private final AuthenticationManager authManager;

  private final ExceptionService exceptionService;

  @Autowired
  public AuthenticationServiceImpl(
      UserRepository userRepository,
      JwtServiceImpl jwtServiceImpl,
      AuthenticationManager authManager,
      ExceptionService exceptionService) {
    this.userRepository = userRepository;
    this.jwtServiceImpl = jwtServiceImpl;
    this.authManager = authManager;
    this.exceptionService = exceptionService;
  }

  @Override
  public ResponseDTO authenticate(AuthenticationRequest request) {
    exceptionService.checkForUserErrors(request);
    User authenticatedUser = userRepository.findByEmail(request.getEmail()).get();
    authManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    String jwtToken =
        jwtServiceImpl.generateToken(
            Map.of(
                "userId", authenticatedUser.getId(),
                "isAdmin", authenticatedUser.getRole() == Role.ADMIN,
                "isVerified", authenticatedUser.isVerified()),
            authenticatedUser);
    return new AuthenticationResponse("ok", jwtToken);
  }
}
