package org.gfa.avusfoxticketbackend.config.services;

import org.gfa.avusfoxticketbackend.config.models.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
  RefreshToken createRefreshToken(String email);

  Optional<RefreshToken> findByToken(String token);

  RefreshToken verifyExpiration(RefreshToken token);

  void saveRefreshToken(RefreshToken refreshToken);
}
