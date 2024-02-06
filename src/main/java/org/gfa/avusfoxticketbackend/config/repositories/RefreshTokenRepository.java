package org.gfa.avusfoxticketbackend.config.repositories;

import org.gfa.avusfoxticketbackend.config.models.RefreshToken;
import org.gfa.avusfoxticketbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByToken(String token);

  Optional<RefreshToken> findByUser(User user);
}
