package com.vetmara.auth.authservice.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@IdClass(UserRoleId.class)
@Table(name = "user_rol")
public class UserRoleEntity {

    @Id
    @Column(nullable = false, length = 253)
    private String username;

    @Id
    @Column(nullable = false, length = 253)
    private String role;

    @Column(name = "granted_date", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime grantedDate;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false,updatable = false)
    private UserEntity user;

}
