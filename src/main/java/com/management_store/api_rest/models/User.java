package com.management_store.api_rest.models;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "core_users")
@Data
public class User {
    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    private String name;
    private String email;
    private String avatar;
    private String avatar_name;
    private Integer status;
    private String password;
    private String reset_password;
    private String verify_token;
    private String verification_code;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
