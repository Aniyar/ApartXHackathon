package com.hackathon.apartxhackathon.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    CLEANER_READ("cleaner:read"),
    CLEANER_UPDATE("cleaner:update"),
    CLEANER_CREATE("cleaner:create"),
    CLEANER_DELETE("cleaner:delete")

    ;

    @Getter
    private final String permission;
}
