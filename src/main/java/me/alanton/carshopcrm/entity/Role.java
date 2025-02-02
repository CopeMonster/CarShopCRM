package me.alanton.carshopcrm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
@Table(name = "role")
public class Role extends BaseEntity implements GrantedAuthority {
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    @Builder.Default
    private List<User> users = new ArrayList<>();

    public Role(String name) {
        this.name = name;
        users = new ArrayList<>();
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
