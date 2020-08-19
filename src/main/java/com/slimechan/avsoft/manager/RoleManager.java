package com.slimechan.avsoft.manager;

import com.slimechan.avsoft.entity.permission.Permission;
import com.slimechan.avsoft.entity.permission.Role;
import com.slimechan.avsoft.manager.impl.RoleManagerImpl;

import java.util.List;

public interface RoleManager {

    RoleManager Instance = new RoleManagerImpl();

    Role getDefault();

    Role getAdmin();

    Role getByName(String roleName);

    void addPermission(String roleName, Permission permission);

    void removePermission(String roleName, Permission permission);

    List<Role> findAll();

}
