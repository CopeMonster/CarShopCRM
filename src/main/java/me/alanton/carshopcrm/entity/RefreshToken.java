package me.alanton.carshopcrm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import me.alanton.carshopcrm.entity.base.BaseEntity;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
@Table(name = "refresh_token")
public class RefreshToken extends BaseEntity {
    @Column(name = "token", nullable = false)
    private String token;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "token_expired_at", nullable = false)
    private Instant tokenExpiredAt;

    @Column(name = "revoked", nullable = false)
    private boolean revoked;
}
