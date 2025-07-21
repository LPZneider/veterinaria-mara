package com.vetmara.auth.authservice.service;

import com.vetmara.auth.authservice.persistence.entity.UserEntity;
import com.vetmara.auth.authservice.persistence.entity.UserRoleEntity;
import com.vetmara.auth.authservice.persistence.entity.UserRoleId;
import com.vetmara.auth.authservice.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(username));
        String[] roles = user.getRoles().stream().map(UserRoleEntity::getRole).toArray(String[]::new);
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(roles)
                .accountLocked(user.getEnabled())
                .disabled(user.getDisabled())
                .build();
    }
}
