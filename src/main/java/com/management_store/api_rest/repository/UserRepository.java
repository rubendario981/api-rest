package com.management_store.api_rest.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.management_store.api_rest.models.User;

public interface UserRepository extends JpaRepository<User, UUID> {}
