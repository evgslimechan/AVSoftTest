package com.slimechan.avsoft.entity.user;

import com.slimechan.avsoft.entity.permission.Permission;

import java.util.List;

public interface AbstractUser {

    List<Permission> getPermissions();

}
