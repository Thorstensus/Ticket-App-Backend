package org.gfa.avusfoxticketbackend.config.services.impl;

import io.github.cdimascio.dotenv.Dotenv;
import org.gfa.avusfoxticketbackend.config.models.RefreshToken;
import org.gfa.avusfoxticketbackend.config.repositories.RefreshTokenRepository;
import org.gfa.avusfoxticketbackend.config.services.RefreshTokenService;
import org.gfa.avusfoxticketbackend.exception.ApiRequestException;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
  private final RefreshTokenRepository refreshTokenRepository;

  private final UserRepository userRepository;

  private static final Dotenv dotenv = Dotenv.configure().load();

  private static final String REFRESH_EXPIRATION_TIME = dotenv.get("REFRESH_EXPIRATION_TIME");

  @Autowired
  public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
    this.refreshTokenRepository = refreshTokenRepository;
    this.userRepository = userRepository;
  }

  @Override public RefreshToken createRefreshToken(String email) {
    Optional<User> userOptional = userRepository.findByEmail(email);
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      RefreshToken refreshToken = new RefreshToken
              (UUID.randomUUID().toString(),
              new Date(System.currentTimeMillis() + Integer.parseInt(REFRESH_EXPIRATION_TIME)),
              user);
      refreshTokenRepository.save(refreshToken);
      return refreshToken;
    } else {
      throw new ApiRequestException("/api","Unknown Error");
    }
  }

  @Override public Optional<RefreshToken> findByToken(String token){
    return refreshTokenRepository.findByToken(token);
  }

  @Override public RefreshToken verifyExpiration(RefreshToken token){
    if(token.getExpiryDate().before(new Date())){
      refreshTokenRepository.delete(token);
      throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
    }
    return token;
  }

  @Override public void saveRefreshToken(RefreshToken refreshToken) {
    refreshTokenRepository.save(refreshToken);
  }
}
