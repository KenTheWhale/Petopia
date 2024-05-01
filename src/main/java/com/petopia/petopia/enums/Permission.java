package com.petopia.petopia.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    USER_CREATE("user:create"),
    USER_READ("user:read"),
    USER_UPDATE("user:update"),
    USER_DELETE("user:delete"),

    GROUP_ADMIN_CREATE("g_admin:create"),
    GROUP_ADMIN_READ("g_admin:read"),
    GROUP_ADMIN_UPDATE("g_admin:update"),
    GROUP_ADMIN_DELETE("g_admin:delete"),

    GROUP_MANAGER_CREATE("g_manager:create"),
    GROUP_MANAGER_READ("g_manager:read"),
    GROUP_MANAGER_UPDATE("g_manager:update"),
    GROUP_MANAGER_DELETE("g_manager:delete"),

    OWNER_CREATE("owner:create"),
    OWNER_READ("owner:read"),
    OWNER_UPDATE("owner:update"),
    OWNER_DELETE("owner:delete"),

    VET_CREATE("vet:create"),
    VET_READ("vet:read"),
    VET_UPDATE("vet:update"),
    VET_DELETE("vet:delete"),

    ADMIN_CREATE("admin:create"),
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete");

    private final String permission;
}
