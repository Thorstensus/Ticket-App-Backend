package org.gfa.avusfoxticketbackend.auth;

import org.gfa.avusfoxticketbackend.config.JwtService;
import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDTO;
import org.gfa.avusfoxticketbackend.enums.Role;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.repositories.UserRepository;
import org.gfa.avusfoxticketbackend.services.ExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import java.util.Map;


@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    private final ExceptionService exceptionService;



    @Autowired
    public AuthenticationService(UserRepository userRepository,
                                 JwtService jwtService,
                                 AuthenticationManager authManager,
                                 ExceptionService exceptionService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authManager = authManager;
        this.exceptionService=exceptionService;
    }

    public ResponseDTO authenticate(AuthenticationRequest request){
        exceptionService.checkForUserErrors(request);
        User authenticatedUser = userRepository.findByEmail(request.getEmail()).get();
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        String jwtToken = jwtService.generateToken(
                Map.of(
                        "userId",authenticatedUser.getId(),
                        "isAdmin",authenticatedUser.getRole() == Role.ADMIN,
                        "isVerified",authenticatedUser.isVerified()),
                authenticatedUser);
        return new AuthenticationResponse("ok",jwtToken);
        }
    }

