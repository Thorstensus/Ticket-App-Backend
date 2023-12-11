package org.gfa.avusfoxticketbackend.controllers;

import org.gfa.avusfoxticketbackend.dtos.ErrorResponse;
import org.gfa.avusfoxticketbackend.dtos.RequestUserDTO;
import org.gfa.avusfoxticketbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RESTController {
    // fields & dependency injection with constructor
    private final UserService userService;

    @Autowired
    public RESTController(UserService userService) {
        this.userService = userService;
    }

    // endpoints
    @PostMapping("/api/users")
    public ResponseEntity registration(@RequestBody(required = false) RequestUserDTO requestUserDTO) {
        if (requestUserDTO == null) {
            return ResponseEntity.status(400).body(new ErrorResponse("Name, email and password are required."));
        } else if(requestUserDTO.getPassword() == null && (requestUserDTO.getName() != null && requestUserDTO.getEmail() != null)) {
            return ResponseEntity.status(400).body(new ErrorResponse("Password is required."));
        } else if(requestUserDTO.getName() == null && (requestUserDTO.getEmail() != null && requestUserDTO.getPassword() != null)) {
            return ResponseEntity.status(400).body(new ErrorResponse("Name is required."));
        } else if(requestUserDTO.getEmail() == null && (requestUserDTO.getName() != null && requestUserDTO.getPassword() != null)) {
            return ResponseEntity.status(400).body(new ErrorResponse("Email is required."));
        } else if(userService.existsByEmail(requestUserDTO.getEmail())) {
            return ResponseEntity.status(400).body(new ErrorResponse("Email is already taken."));
        } else if(requestUserDTO.getPassword().length() < 8) {
            return ResponseEntity.status(400).body(new ErrorResponse("Password must be at least 8 characters."));
        } else {
            return ResponseEntity.status(200).body(userService.userToResponseUserDTOConverter(userService.newUserCreatedAndReturned(requestUserDTO)));
        }
    }
}
