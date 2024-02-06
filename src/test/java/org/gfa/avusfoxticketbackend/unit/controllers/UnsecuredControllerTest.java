package org.gfa.avusfoxticketbackend.unit.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import org.gfa.avusfoxticketbackend.config.models.RefreshToken;
import org.gfa.avusfoxticketbackend.config.services.JwtService;
import org.gfa.avusfoxticketbackend.config.services.RefreshTokenService;
import org.gfa.avusfoxticketbackend.controllers.UnsecuredController;
import org.gfa.avusfoxticketbackend.dtos.authdtos.AuthenticationResponse;
import org.gfa.avusfoxticketbackend.dtos.authdtos.RefreshTokenRequest;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.services.AuthenticationService;
import org.gfa.avusfoxticketbackend.services.NewsService;
import org.gfa.avusfoxticketbackend.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = UnsecuredController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class UnsecuredControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private RefreshTokenService refreshTokenService;

  @MockBean private JwtService jwtService;

  @MockBean private UserService userService;

  @MockBean private AuthenticationService authenticationService;

  @MockBean private NewsService newsService;

  @Test
  void refreshTokenGivesBackResponse() throws Exception {

    String token = "refreshing";
    RefreshTokenRequest request = new RefreshTokenRequest(token);

    User user = new User("John", "johnny@kek.lmao", "1337");
    RefreshToken refreshToken =
        new RefreshToken(token, new Date(System.currentTimeMillis() + 1000000), user);

    AuthenticationResponse expected = new AuthenticationResponse("ok", token, "jwtxdd");

    when(refreshTokenService.generateNewToken(request)).thenReturn(expected);

    mockMvc
        .perform(
            post("/api/refresh-token")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    verify(refreshTokenService, times(1)).generateNewToken(request);
  }
}
