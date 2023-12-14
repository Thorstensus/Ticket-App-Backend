package org.gfa.avusfoxticketbackend.auth;

import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDTO;
import org.gfa.avusfoxticketbackend.exception.ApiExceptionHandler;
import org.gfa.avusfoxticketbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final AuthenticationService authService;

    @Autowired
    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/users/login")
    public ResponseDTO authenticate(@RequestBody AuthenticationRequest request){
        return authService.authenticate(request);
    }

}
