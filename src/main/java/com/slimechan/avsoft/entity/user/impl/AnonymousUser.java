package com.slimechan.avsoft.entity.user.impl;

import com.slimechan.avsoft.entity.permission.Permission;
import com.slimechan.avsoft.entity.user.AbstractUser;
import com.slimechan.avsoft.manager.PropertiesManager;

import java.util.Arrays;
import java.util.List;

public class AnonymousUser implements AbstractUser {

    private static final Permission[] DEFAULT_PERMISSIONS;

    static {
        String[] stringPermissions = PropertiesManager.Instance.getProperty("role.default.anonymous.permissions").split(",");
        DEFAULT_PERMISSIONS = new Permission[stringPermissions.length];
        for (int i = 0; i < stringPermissions.length; i++) {
            if (stringPermissions[i].isEmpty()) continue;
            DEFAULT_PERMISSIONS[i] = Permission.toPermission(stringPermissions[i]);
        }
    }

    @Override
    public List<Permission> getPermissions() {
        return Arrays.asList(DEFAULT_PERMISSIONS);
    }
}
