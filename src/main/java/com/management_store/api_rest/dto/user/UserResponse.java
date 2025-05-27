package com.management_store.api_rest.dto.user;

import java.util.UUID;

public record UserResponse(UUID id, String name, String email, String roleName) {
}
