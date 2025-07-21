package com.vetmara.auth.authservice.persistence.entity;

import com.vetmara.auth.authservice.persistence.enums.AuthTypeEnum;
import com.vetmara.auth.authservice.persistence.enums.HttpMethodEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "security_rule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SecurityRuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String urlPattern;

    @Enumerated(EnumType.STRING)
    private HttpMethodEnum httpMethod;

    @Enumerated(EnumType.STRING)
    private AuthTypeEnum authType;

    private boolean permitAll;

    @ManyToMany
    @JoinTable(
            name = "security_rule_roles",
            joinColumns = @JoinColumn(name = "rule_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<RoleEntity> roles;
}
