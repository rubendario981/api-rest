package com.management_store.api_rest.controllers;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.management_store.api_rest.dto.PagedResponse;
import com.management_store.api_rest.dto.user.CreateUserRequest;
import com.management_store.api_rest.dto.user.UpdateUserRequest;
import com.management_store.api_rest.dto.user.UserResponse;
import com.management_store.api_rest.dto.user.updateUserStatusRequest;
import com.management_store.api_rest.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    
    /* @GetMapping("/hi")
    public String sayHello() {
        return "Hello World";
    }
    
    @GetMapping("/")
    public List<User> getUsers() {
        return userService.getUsers();
    } */

    @PreAuthorize("hasAuthority('user.read')")
    @GetMapping("/")
    @Parameter(name = "page", description = "Page number (0-based)", example = "0")
    @Parameter(name = "size", description = "Page size", example = "10")
    public PagedResponse<UserResponse> getUsers(
        @RequestParam(name = "page", defaultValue = "0") 
        int page, @RequestParam(name = "size", defaultValue = "10") 
        int size
    ) {
        return userService.getUsers(page, size);
    }

    @PreAuthorize("hasAuthority('user.read')")
    @GetMapping("/search")
    @Operation(summary = "List users with pagination and search")
    public Page<UserResponse> listUsers(
        @RequestParam(defaultValue = "") String query,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "name") String sortBy,
        @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return userService.searchUsers(query, pageable);
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

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAuthority('user.update')")
    @Operation(summary = "Update user status")
    public UserResponse updateUserStatus(@PathVariable UUID id, @RequestBody updateUserStatusRequest request) {
        return userService.updateUserStatus(id, request);
    }

    @PreAuthorize("hasAuthority('user.delete')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user (soft delete)")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }
}
