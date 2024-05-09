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

    GROUP_ADMIN_CREATE("ga:create"),
    GROUP_ADMIN_READ("ga:read"),
    GROUP_ADMIN_UPDATE("ga:update"),
    GROUP_ADMIN_DELETE("ga:delete"),

    GROUP_MANAGER_CREATE("gm:create"),
    GROUP_MANAGER_READ("gm:read"),
    GROUP_MANAGER_UPDATE("gm:update"),
    GROUP_MANAGER_DELETE("gm:delete"),

    OWNER_CREATE("owner:create"),
    OWNER_READ("owner:read"),
    OWNER_UPDATE("owner:update"),
    OWNER_DELETE("owner:delete"),

    SERVICE_CENTER_MANAGER_CREATE("scm:create"),
    SERVICE_CENTER_MANAGER_READ("scm:read"),
    SERVICE_CENTER_MANAGER_UPDATE("scm:update"),
    SERVICE_CENTER_MANAGER_DELETE("scm:delete"),

    SERVICE_PROVIDER_CREATE("sp:create"),
    SERVICE_PROVIDER_READ("sp:read"),
    SERVICE_PROVIDER_UPDATE("sp:update"),
    SERVICE_PROVIDER_DELETE("sp:delete"),

    ADMIN_CREATE("admin:create"),
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete");

    private final String permission;
}
