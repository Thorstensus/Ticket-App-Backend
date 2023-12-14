package org.gfa.avusfoxticketbackend.controllers;

import org.gfa.avusfoxticketbackend.dtos.authdtos.AuthenticationRequest;
import org.gfa.avusfoxticketbackend.auth.AuthenticationService;
import org.gfa.avusfoxticketbackend.dtos.RequestUserDTO;
import org.gfa.avusfoxticketbackend.dtos.ResponseUserDTO;
import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDTO;
import org.gfa.avusfoxticketbackend.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final AuthenticationService authService;

    private final UserServiceImpl userService;

    @Autowired
    public AuthenticationController(UserServiceImpl userService,AuthenticationService authService) {
        this.userService=userService;
        this.authService = authService;
    }

    @PostMapping("/users/login")
    public ResponseDTO authenticate(@RequestBody AuthenticationRequest request){
        return authService.authenticate(request);
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUserDTO> registration(@RequestBody(required = false) RequestUserDTO requestUserDTO) {
        return ResponseEntity.status(200).body(userService.newUserCreatedAndReturned(requestUserDTO));
    }

}
