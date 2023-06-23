package com.hackathon.apartxhackathon.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.hackathon.apartxhackathon.user.Permission.ADMIN_CREATE;
import static com.hackathon.apartxhackathon.user.Permission.ADMIN_DELETE;
import static com.hackathon.apartxhackathon.user.Permission.ADMIN_READ;
import static com.hackathon.apartxhackathon.user.Permission.ADMIN_UPDATE;
import static com.hackathon.apartxhackathon.user.Permission.CLEANER_CREATE;
import static com.hackathon.apartxhackathon.user.Permission.CLEANER_DELETE;
import static com.hackathon.apartxhackathon.user.Permission.CLEANER_READ;
import static com.hackathon.apartxhackathon.user.Permission.CLEANER_UPDATE;

@RequiredArgsConstructor
public enum Role {

  USER(Collections.emptySet()),
  ADMIN(
          Set.of(
                  ADMIN_READ,
                  ADMIN_UPDATE,
                  ADMIN_DELETE,
                  ADMIN_CREATE
          )
  ),
  CLEANER(
          Set.of(
                  CLEANER_READ,
                  CLEANER_UPDATE,
                  CLEANER_DELETE,
                  CLEANER_CREATE
          )
  )

  ;

  @Getter
  private final Set<Permission> permissions;

  public List<SimpleGrantedAuthority> getAuthorities() {
    var authorities = getPermissions()
            .stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toList());
    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return authorities;
  }
}
