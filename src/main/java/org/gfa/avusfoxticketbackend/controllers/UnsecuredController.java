package org.gfa.avusfoxticketbackend.controllers;

import org.gfa.avusfoxticketbackend.config.services.RefreshTokenService;
import org.gfa.avusfoxticketbackend.dtos.*;
import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDTO;
import org.gfa.avusfoxticketbackend.dtos.authdtos.AuthenticationRequestDTO;
import org.gfa.avusfoxticketbackend.dtos.authdtos.AuthenticationResponseDTO;
import org.gfa.avusfoxticketbackend.dtos.authdtos.RefreshTokenRequestDTO;
import org.gfa.avusfoxticketbackend.logging.LogHandlerInterceptor;
import org.gfa.avusfoxticketbackend.services.AuthenticationService;
import org.gfa.avusfoxticketbackend.services.NewsService;
import org.gfa.avusfoxticketbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UnsecuredController {

  private final AuthenticationService authService;

  private final UserService userService;

  private final NewsService newsService;

  private final RefreshTokenService refreshTokenService;

  @Autowired
  public UnsecuredController(
          UserService userService, AuthenticationService authService, NewsService newsService, RefreshTokenService refreshTokenService) {
    this.userService = userService;
    this.authService = authService;
    this.newsService = newsService;
    this.refreshTokenService = refreshTokenService;
  }

  @PostMapping("/users/login")
  public ResponseDTO authenticate(@RequestBody AuthenticationRequestDTO request) {
    LogHandlerInterceptor.object = request;
    return authService.authenticate(request);
  }

  @PostMapping("/users")
  public ResponseEntity<ResponseUserDTO> registration(
      @RequestBody(required = false) RequestUserDTO requestUserDTO) {
    LogHandlerInterceptor.object = requestUserDTO;
    return ResponseEntity.status(200).body(userService.newUserCreatedAndReturned(requestUserDTO));
  }

  @GetMapping("/news")
  public ResponseEntity<ArticlesResponseDTO> getNews() {
    return ResponseEntity.status(200).body(newsService.getAllNews());
  }

  @GetMapping("/news/{search}")
  public ResponseEntity<ArticlesResponseDTO> searchNews(@PathVariable(required = true) String search) {
    LogHandlerInterceptor.object = search;
    return ResponseEntity.status(200).body(newsService.getAllNewsByTitleOrDescriptionContaining(search));
  }

  @GetMapping("/email-verification/{token}")
  public ResponseEntity<String> emailVerification(@PathVariable String token) {
    LogHandlerInterceptor.object = token;
    userService.verifyUserByVerificationToken(token);
    return ResponseEntity.status(200).body("User verified");
  }

  @PostMapping("/refresh-token")
  public AuthenticationResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
    LogHandlerInterceptor.object = refreshTokenRequestDTO;
    return refreshTokenService.generateNewToken(refreshTokenRequestDTO);
  }
}
