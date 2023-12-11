package org.gfa.avusfoxticketbackend.controllers;

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
    public ResponseEntity registration(@RequestBody ) {

    }
}
