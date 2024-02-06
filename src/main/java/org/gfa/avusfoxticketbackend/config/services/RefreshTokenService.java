package org.gfa.avusfoxticketbackend.config.services;

import java.util.Optional;
import org.gfa.avusfoxticketbackend.config.models.RefreshToken;
import org.gfa.avusfoxticketbackend.dtos.authdtos.AuthenticationResponse;
import org.gfa.avusfoxticketbackend.dtos.authdtos.RefreshTokenRequest;
import org.gfa.avusfoxticketbackend.models.User;

public interface RefreshTokenService {
  RefreshToken createRefreshToken(String email);

  Optional<RefreshToken> findByToken(String token);

  Optional<RefreshToken> findByUser(User user);

  RefreshToken verifyExpiration(RefreshToken token);

  void saveRefreshToken(RefreshToken refreshToken);

  void deleteRefreshToken(RefreshToken refreshToken);

  AuthenticationResponse generateNewToken(RefreshTokenRequest refreshTokenRequest);

  int getExpirationTime();
}
