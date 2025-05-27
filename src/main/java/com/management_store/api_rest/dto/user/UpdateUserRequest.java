package com.management_store.api_rest.dto.user;

import java.util.UUID;

public record UpdateUserRequest(
  String name,
  String email,
  Integer status,
  UUID roleId) {
}
