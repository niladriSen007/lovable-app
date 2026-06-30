package com.niladri.lovable_app.security;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserInfoService implements UserDetails {

    @Getter
    Long id;
    String email;
    @Getter
    String name;
    String password;
    Collection<? extends GrantedAuthority> authorities;

    public UserInfoService(Long userId,String email, String name, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = userId;
        this.email = email;
        this.name = name;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
