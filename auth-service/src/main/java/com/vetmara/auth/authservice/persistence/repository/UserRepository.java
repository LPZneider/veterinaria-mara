package com.vetmara.auth.authservice.persistence.repository;

import com.vetmara.auth.authservice.persistence.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, String> {
}
