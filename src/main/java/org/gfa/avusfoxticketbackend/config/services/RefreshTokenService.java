package org.gfa.avusfoxticketbackend.config.services;

import org.gfa.avusfoxticketbackend.config.models.RefreshToken;
import org.gfa.avusfoxticketbackend.dtos.authdtos.AuthenticationResponseDTO;
import org.gfa.avusfoxticketbackend.dtos.authdtos.RefreshTokenRequestDTO;
import org.gfa.avusfoxticketbackend.models.User;

import java.util.Optional;

public interface RefreshTokenService {
  RefreshToken createRefreshToken(String email);

  Optional<RefreshToken> findByToken(String token);

  Optional<RefreshToken> findByUser(User user);

  RefreshToken verifyExpiration(RefreshToken token);

  void saveRefreshToken(RefreshToken refreshToken);

  void deleteRefreshToken(RefreshToken refreshToken);

  AuthenticationResponseDTO generateNewToken(RefreshTokenRequestDTO refreshTokenRequestDTO);

  int getExpirationTime();
}
