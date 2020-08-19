package com.slimechan.avsoft.manager.impl;

import com.slimechan.avsoft.dao.entityrepository.RoleRepository;
import com.slimechan.avsoft.entity.permission.Permission;
import com.slimechan.avsoft.entity.permission.Role;
import com.slimechan.avsoft.manager.RoleManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoleManagerImpl implements RoleManager {
    @Override
    public Role getDefault() {
        return getByName(RoleRepository.DEFAULT_GROUP_NAME);
    }

    @Override
    public Role getAdmin() {
        return getByName(RoleRepository.ADMIN_GROUP_NAME);
    }

    @Override
    public Role getByName(String roleName) {
        return RoleRepository.getInstance().findByName(roleName);
    }

    @Override
    public void addPermission(String roleName, Permission permission) {
        Role role = getByName(roleName);
        Set<Permission> permissions = new HashSet<>(role.getPermissions());
        permissions.add(permission);
        role.setPermissions(permissions.toArray(new Permission[0]));
        RoleRepository.getInstance().save(role);
    }

    @Override
    public void removePermission(String roleName, Permission permission) {
        Role role = getByName(roleName);
        Set<Permission> permissions = new HashSet<>(role.getPermissions());
        permissions.remove(permission);
        role.setPermissions(permissions.toArray(new Permission[0]));
        RoleRepository.getInstance().save(role);
    }

    @Override
    public List<Role> findAll() {
        return RoleRepository.getInstance().findAll();
    }
}
