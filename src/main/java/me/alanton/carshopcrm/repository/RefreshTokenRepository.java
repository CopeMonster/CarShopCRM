package me.alanton.carshopcrm.repository;

import me.alanton.carshopcrm.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByIdAndTokenExpiredAt(UUID id, Instant tokenExpiredAt);

    Optional<RefreshToken> findByToken(String token);
}
