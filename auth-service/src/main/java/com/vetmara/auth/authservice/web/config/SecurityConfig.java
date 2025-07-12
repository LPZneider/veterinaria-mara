package com.vetmara.auth.authservice.web.config;

import com.vetmara.auth.authservice.persistence.entity.RoleEntity;
import com.vetmara.auth.authservice.persistence.entity.SecurityRuleEntity;
import com.vetmara.auth.authservice.persistence.enums.AuthTypeEnum;
import com.vetmara.auth.authservice.persistence.enums.HttpMethodEnum;
import com.vetmara.auth.authservice.persistence.repository.SecurityRuleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final SecurityRuleRepository securityRuleRepository;

    public SecurityConfig(JwtFilter jwtFilter, SecurityRuleRepository securityRuleRepository) {
        this.jwtFilter = jwtFilter;
        this.securityRuleRepository = securityRuleRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        List<SecurityRuleEntity> rules = securityRuleRepository.findAll();
        http.
                csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    for (SecurityRuleEntity rule : rules) {
                        RequestMatcher matcher;
                        if (rule.getHttpMethod() == HttpMethodEnum.ALL) {
                            matcher = AntPathRequestMatcher.antMatcher(rule.getUrlPattern());
                        } else {
                            matcher = new AntPathRequestMatcher(rule.getUrlPattern(), rule.getHttpMethod().name());
                        }
                        if (rule.isPermitAll()) {
                            auth.requestMatchers(matcher).permitAll();
                        } else if (rule.getAuthType() == AuthTypeEnum.ROLE) {
                            for (RoleEntity role : rule.getRoles()) {
                                auth.requestMatchers(matcher).hasRole(role.getName());
                            }
                        } else if (rule.getAuthType() == AuthTypeEnum.AUTHORITY) {
                            for (RoleEntity role : rule.getRoles()) {
                                auth.requestMatchers(matcher).hasAuthority(role.getName());
                            }
                        }
                    }
                })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
