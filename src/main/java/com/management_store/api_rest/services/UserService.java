package com.management_store.api_rest.services;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.management_store.api_rest.dto.user.CreateUserRequest;
import com.management_store.api_rest.dto.user.UpdateUserRequest;
import com.management_store.api_rest.dto.user.UserResponse;
import com.management_store.api_rest.models.Role;
import com.management_store.api_rest.models.User;
import com.management_store.api_rest.repository.RoleRepository;
import com.management_store.api_rest.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final RoleRepository roleRepository;

  public User createUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  public List<User> getUsers() {
    return userRepository.findAll();
  }

  public UserResponse getUserById(UUID id) {
    return userRepository.findById(id)
      .map(this::toResponse)
      .orElseThrow(() -> new RuntimeException("No existe el usuario"));
  }

  public UserResponse createUser(CreateUserRequest request) {
    Role role = roleRepository.findById(request.roleId())
      .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
    
    userRepository.findByEmail(request.email())
      .ifPresent(u -> { throw new RuntimeException("El email ya existe"); });
    
    User user = new User();
    user.setName(request.name());
    user.setEmail(request.email());
    user.setPassword(passwordEncoder.encode(request.password()));
    user.setStatus(1);
    user.setRole(role);
    
    userRepository.save(user);
    return toResponse(user);
  }

  public UserResponse updateUser(UUID id, UpdateUserRequest request) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    userRepository.findByEmail(request.email())
        .ifPresent(u -> { throw new RuntimeException("El email ya esta en uso"); });

    if (request.name() != null && !request.name().isBlank()) {
        user.setName(request.name());
    }

    if (request.email() != null && !request.email().isBlank()) {
        user.setEmail(request.email());
    }

    if (request.status() != null) {
        user.setStatus(request.status());
    }

    if (request.roleId() != null) {
        Role role = roleRepository.findById(request.roleId())
            .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        user.setRole(role);
    }

    return toResponse(userRepository.save(user));
  }

  public void deleteUser(UUID id) {
    if (!userRepository.existsById(id)) {
      throw new RuntimeException("No existe el usuario");
    }
    userRepository.deleteById(id);
  }

  private UserResponse toResponse(User user) {
    return new UserResponse(
      user.getId(),
      user.getName(),
      user.getEmail(),
      user.getRole().getName()
    );
  }
}
