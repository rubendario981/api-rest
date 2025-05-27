package com.management_store.api_rest.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management_store.api_rest.dto.user.CreateUserRequest;
import com.management_store.api_rest.dto.user.UpdateUserRequest;
import com.management_store.api_rest.dto.user.UserResponse;
import com.management_store.api_rest.models.User;
import com.management_store.api_rest.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "User management")
@SecurityRequirement(name = "BearerAuth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/hi")
    public String sayHello() {
        return "Hello World";
    }
    
    @GetMapping("/")
    public List<User> getUsers() {
        return userService.getUsers();
    }
    
    @PreAuthorize("hasAuthority('user.read')")
    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    @PreAuthorize("hasAuthority('user.create')")
    @PostMapping("/")
    @Operation(summary = "Create a new user")
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @PreAuthorize("hasAuthority('user.update')")
    @PatchMapping("/{id}")
    @Operation(summary = "Update a user")
    public UserResponse updateUser(@PathVariable UUID id, @RequestBody UpdateUserRequest request) {
        return userService.updateUser(id, request);
    }

    @PreAuthorize("hasAuthority('user.delete')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }
}
