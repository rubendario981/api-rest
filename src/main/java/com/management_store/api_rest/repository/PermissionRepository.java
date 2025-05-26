package com.management_store.api_rest.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.management_store.api_rest.models.Permission;

public interface PermissionRepository extends JpaRepository<Permission, UUID> {
  Optional<Permission> findByValue(String value);
}
