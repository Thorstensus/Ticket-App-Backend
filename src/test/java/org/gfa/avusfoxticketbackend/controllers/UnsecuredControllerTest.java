package org.gfa.avusfoxticketbackend.controllers;

import org.gfa.avusfoxticketbackend.dtos.authdtos.AuthenticationResponse;
import org.gfa.avusfoxticketbackend.dtos.authdtos.RefreshTokenRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = UnsecuredController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class UnsecuredControllerTest {

  @Test
  void refreshTokenGivesBackResponse() {

    RefreshTokenRequest request = new RefreshTokenRequest("tokenxdd");
    AuthenticationResponse expected = new AuthenticationResponse("ok","tokenxdd","jwtxdd");
  }
}