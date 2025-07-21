package com.vetmara.auth.authservice.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "auth_user")
public class UserEntity {

    @Id
    @Column(nullable = false, length = 253)
    private String username;

    @Column(nullable = false, length = 253)
    private String password;

    @Column(length = 60)
    private String email;

    @Column(nullable = false)
    private Boolean enabled;

    @Column(nullable = false)
    private Boolean disabled;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<UserRoleEntity> roles;
}
