package com.management_store.api_rest.security;

import com.management_store.api_rest.models.Permission;
import com.management_store.api_rest.models.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

@Getter
public class UserPrincipal implements org.springframework.security.core.userdetails.UserDetails {

    private final UUID id;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(UUID id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal build(User user) {
        List<SimpleGrantedAuthority> authorities = user.getRole().getPermissions().stream()
          .map(Permission::getValue)
          .map(SimpleGrantedAuthority::new)
          .toList();

        return new UserPrincipal(user.getId(), user.getEmail(), user.getPassword(), authorities);
    }

    @Override public String getUsername() { return email; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
