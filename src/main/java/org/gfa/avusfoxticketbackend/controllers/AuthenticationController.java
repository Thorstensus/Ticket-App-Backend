package org.gfa.avusfoxticketbackend.controllers;

import org.gfa.avusfoxticketbackend.dtos.RequestUserDTO;
import org.gfa.avusfoxticketbackend.services.AuthenticationService;
import org.gfa.avusfoxticketbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

  private final AuthenticationService authService;
  private final UserService userService;

  @Autowired
  public AuthenticationController(AuthenticationService authService, UserService userService) {
    this.authService = authService;
    this.userService = userService;
  }

  @PostMapping("/users")
  public ResponseEntity registration(@RequestBody(required = false) RequestUserDTO requestUserDTO) {
    return ResponseEntity.status(200)
        .body(
            userService.userToResponseUserDTOConverter(
                userService.newUserCreatedAndReturned(requestUserDTO)));
  }
}
