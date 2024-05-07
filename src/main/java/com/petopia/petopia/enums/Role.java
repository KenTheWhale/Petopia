package com.petopia.petopia.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;

import static com.petopia.petopia.enums.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER(
            Set.of(
                    USER_CREATE,
                    USER_READ,
                    USER_UPDATE,
                    USER_DELETE
            )
    ),
    GROUP_MANAGER(
            Set.of(
                    GROUP_MANAGER_CREATE,
                    GROUP_MANAGER_READ,
                    GROUP_MANAGER_UPDATE,
                    GROUP_MANAGER_DELETE
            )
    ),
    GROUP_ADMIN(
            Set.of(
                    GROUP_MANAGER_CREATE,
                    GROUP_MANAGER_READ,
                    GROUP_MANAGER_UPDATE,
                    GROUP_MANAGER_DELETE,
                    GROUP_ADMIN_CREATE,
                    GROUP_ADMIN_READ,
                    GROUP_ADMIN_UPDATE,
                    GROUP_ADMIN_DELETE
            )
    ),
    SHOP_OWNER(
            Set.of(
                    OWNER_CREATE,
                    OWNER_READ,
                    OWNER_UPDATE,
                    OWNER_DELETE
            )
    ),
    SERVICE_CENTER_MANAGER(
            Set.of(
                    SERVICE_CENTER_MANAGER_CREATE,
                    SERVICE_CENTER_MANAGER_READ,
                    SERVICE_CENTER_MANAGER_UPDATE,
                    SERVICE_CENTER_MANAGER_DELETE
            )
    ),
    SERVICE_PROVIDER(
            Set.of(
                    SERVICE_PROVIDER_CREATE,
                    SERVICE_PROVIDER_READ,
                    SERVICE_PROVIDER_UPDATE,
                    SERVICE_PROVIDER_DELETE
            )
    ),
    ADMIN(
            Set.of(
                    ADMIN_CREATE,
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE
            )
    );

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var author = new java.util.ArrayList<>(getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .toList());

        author.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return author;
    }
}
