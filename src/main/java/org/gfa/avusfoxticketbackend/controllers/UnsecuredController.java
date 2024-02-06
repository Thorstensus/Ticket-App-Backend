package org.gfa.avusfoxticketbackend.controllers;

import java.util.List;
import org.gfa.avusfoxticketbackend.config.services.JwtService;
import org.gfa.avusfoxticketbackend.config.services.RefreshTokenService;
import org.gfa.avusfoxticketbackend.dtos.*;
import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDTO;
import org.gfa.avusfoxticketbackend.dtos.authdtos.AuthenticationRequest;
import org.gfa.avusfoxticketbackend.dtos.authdtos.AuthenticationResponse;
import org.gfa.avusfoxticketbackend.dtos.authdtos.RefreshTokenRequest;
import org.gfa.avusfoxticketbackend.exception.ApiRequestException;
import org.gfa.avusfoxticketbackend.logging.LogHandlerInterceptor;
import org.gfa.avusfoxticketbackend.models.News;
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

  private final JwtService jwtService;

  @Autowired
  public UnsecuredController(
      UserService userService,
      AuthenticationService authService,
      NewsService newsService,
      RefreshTokenService refreshTokenService,
      JwtService jwtService) {
    this.userService = userService;
    this.authService = authService;
    this.newsService = newsService;
    this.refreshTokenService = refreshTokenService;
    this.jwtService = jwtService;
  }

  @PostMapping("/users/login")
  public ResponseDTO authenticate(@RequestBody AuthenticationRequest request) {
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
  public ResponseEntity<List<NewsResponseDTO>> getNews() {
    return ResponseEntity.status(200).body(newsService.getAllNewsDTOs());
  }

  @GetMapping("/news/")
  public ResponseEntity searchNews(@RequestParam(required = true) String search) {
    LogHandlerInterceptor.object = search;
    List<News> searchedNews = newsService.findAllNewsByTitleOrDescriptionContaining(search);
    if (!search.isEmpty() && !searchedNews.isEmpty()) {
      return ResponseEntity.status(200).body(new ArticlesResponseDTO(searchedNews));
    } else {
      throw new ApiRequestException("/api/news", "No news matching the searched text found.");
    }
  }

  @GetMapping("/email-verification/{token}")
  public ResponseEntity<String> emailVerification(@PathVariable String token) {
    LogHandlerInterceptor.object = token;
    userService.verifyUserByVerificationToken(token);
    return ResponseEntity.status(200).body("User verified");
  }

  @PostMapping("/refresh-token")
  public AuthenticationResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
    LogHandlerInterceptor.object = refreshTokenRequest;
    return refreshTokenService.generateNewToken(refreshTokenRequest);
  }
}
