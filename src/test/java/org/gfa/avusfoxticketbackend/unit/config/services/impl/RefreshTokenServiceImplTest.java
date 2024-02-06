package org.gfa.avusfoxticketbackend.unit.config.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;
import org.gfa.avusfoxticketbackend.config.models.RefreshToken;
import org.gfa.avusfoxticketbackend.config.repositories.RefreshTokenRepository;
import org.gfa.avusfoxticketbackend.config.services.JwtService;
import org.gfa.avusfoxticketbackend.config.services.RefreshTokenService;
import org.gfa.avusfoxticketbackend.config.services.impl.RefreshTokenServiceImpl;
import org.gfa.avusfoxticketbackend.dtos.authdtos.AuthenticationResponse;
import org.gfa.avusfoxticketbackend.dtos.authdtos.RefreshTokenRequest;
import org.gfa.avusfoxticketbackend.exception.ApiRequestException;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceImplTest {

  @Mock RefreshTokenRepository refreshTokenRepository;

  @Mock UserRepository userRepository;

  @Mock JwtService jwtService;

  private RefreshTokenService refreshTokenService;

  @BeforeEach
  void setUp() {
    refreshTokenService =
        new RefreshTokenServiceImpl(refreshTokenRepository, userRepository, jwtService);
  }

  @Test
  void verifyTokenThrows() {
    RefreshToken refreshToken = new RefreshToken("refreshToken", new Date(), new User());

    doNothing().when(refreshTokenRepository).delete(any(RefreshToken.class));

    ApiRequestException exception =
        assertThrows(
            ApiRequestException.class,
            () -> {
              refreshTokenService.verifyExpiration(refreshToken);
            });

    assertEquals("/api/refresh-token", exception.getEndpoint());
    assertEquals(
        "refreshToken Refresh token is expired. Please log in again!", exception.getMessage());
  }

  @Test
  void generateTokenGivesOkResponse() {

    String token = "hahaxd";
    RefreshTokenRequest request = new RefreshTokenRequest(token);

    User user = new User("johnny", "heresjohnny@test.com", "gonk123");
    RefreshToken refreshToken =
        new RefreshToken("lmao", new Date(System.currentTimeMillis() + 10000), user);

    when(refreshTokenService.findByToken(token)).thenReturn(Optional.of(refreshToken));
    when(jwtService.generateToken(user)).thenReturn("jwtMock");

    AuthenticationResponse expected = new AuthenticationResponse("ok", token, "jwtMock");

    assertEquals(expected, refreshTokenService.generateNewToken(request));
  }

  @Test
  void testGenerateNewTokenException() {

    RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest("yourRefreshToken");

    when(refreshTokenRepository.findByToken(refreshTokenRequest.getToken()))
        .thenReturn(Optional.empty());

    ApiRequestException exception =
        assertThrows(
            ApiRequestException.class,
            () -> {
              refreshTokenService.generateNewToken(refreshTokenRequest);
            });

    assertEquals("/api/refresh-token", exception.getEndpoint());
    assertEquals("Refresh Token does not exist!", exception.getMessage());
  }
}
