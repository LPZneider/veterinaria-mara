package com.vetmara.auth.authservice.persistence.repository;

import com.vetmara.auth.authservice.persistence.entity.SecurityRuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityRuleRepository extends JpaRepository<SecurityRuleEntity, Long> {
}
