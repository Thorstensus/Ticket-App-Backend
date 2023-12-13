package org.gfa.avusfoxticketbackend.auth;

import org.gfa.avusfoxticketbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

}
