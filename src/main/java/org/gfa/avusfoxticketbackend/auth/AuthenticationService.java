package org.gfa.avusfoxticketbackend.auth;

import org.gfa.avusfoxticketbackend.config.JwtService;
import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDTO;
import org.gfa.avusfoxticketbackend.exception.ApiExceptionHandler;
import org.gfa.avusfoxticketbackend.exception.ApiRequestException;
import org.gfa.avusfoxticketbackend.models.Role;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    private final PasswordEncoder passwordEncoder;



    @Autowired
    public AuthenticationService(UserRepository userRepository,
                                 JwtService jwtService,
                                 AuthenticationManager authManager,
                                 PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authManager = authManager;
        this.passwordEncoder=passwordEncoder;
    }

    public ResponseDTO authenticate(AuthenticationRequest request){
        if ((request.getPassword() == null || request.getPassword().isEmpty()) && (request.getEmail() == null || request.getEmail().isEmpty())) {
            throw new ApiRequestException("/api/users/login", "All fields are required.");
        } else if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new ApiRequestException("/api/users/login", "Email is required.");
        } else if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new ApiRequestException("/api/users/login", "Password is required.");
        } else {
            Optional<User> user = userRepository.findByEmail(request.getEmail());
            if (user.isEmpty() || !passwordEncoder.matches(request.getPassword(),user.get().getPassword())) {
                throw new ApiRequestException("/api/users/login", "Email or password is incorrect.");
            } else {
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()));
                User authenticatedUser = user.get();
                String jwtToken = jwtService.generateToken(
                        Map.of(
                                "userId",authenticatedUser.getId(),
                                "isAdmin",authenticatedUser.getRole() == Role.ADMIN,
                                "isVerified",authenticatedUser.isVerified()),
                        authenticatedUser);
                return new AuthenticationResponse("ok",jwtToken);
            }
        }
    }
}
