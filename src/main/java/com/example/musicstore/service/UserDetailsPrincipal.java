package com.example.musicstore.service;

import com.example.musicstore.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsPrincipal implements UserDetails {
    private final long idUser;
    private final String username;
    private final String email;
    @JsonIgnore
    private final String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsPrincipal(long id, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.idUser = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public UserDetailsPrincipal(long idUser, String username, String email, String password, List<GrantedAuthority> authorities) {
        this.idUser=idUser;
        this.username=username;
        this.password=password;
        this.email=email;
    }

    public static UserDetailsPrincipal build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                .collect(Collectors.toList());
        return new UserDetailsPrincipal(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
