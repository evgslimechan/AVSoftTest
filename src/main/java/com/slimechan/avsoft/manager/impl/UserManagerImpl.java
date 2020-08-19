package com.slimechan.avsoft.manager.impl;

import com.slimechan.avsoft.dao.entityrepository.RoleRepository;
import com.slimechan.avsoft.dao.entityrepository.UserRepository;
import com.slimechan.avsoft.entity.permission.Permission;
import com.slimechan.avsoft.entity.user.AuthUser;
import com.slimechan.avsoft.entity.user.impl.SimpleUser;
import com.slimechan.avsoft.manager.UserManager;
import com.slimechan.avsoft.util.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

public class UserManagerImpl implements UserManager {
    @Override
    public boolean exists(String username) {
        SimpleUser example = new SimpleUser();
        example.setUsername(username);
        List<SimpleUser> registered = UserRepository.getInstance().findByExample(example);
        return registered.size() > 0;
    }

    @Override
    public SimpleUser getByName(String username) {
        SimpleUser example = new SimpleUser();
        example.setUsername(username);
        List<SimpleUser> registered = UserRepository.getInstance().findByExample(example);
        if (registered.size() > 0) return registered.get(0);
        else return null;
    }

    @Override
    public SimpleUser register(String username, String password) {
        SimpleUser example = new SimpleUser();
        example.setUsername(username);
        example.setPassword(PasswordEncoder.hash(password));
        example.setRole(RoleRepository.getInstance().findByName(RoleRepository.DEFAULT_GROUP_NAME));

        return UserRepository.getInstance().save(example);
    }

    @Override
    public boolean auth(String username, String password) {
        SimpleUser user = getByName(username);
        return PasswordEncoder.checkPassword(password, user.getPassword());
    }

    @Override
    public void addPermission(String userName, Permission permission) {
        SimpleUser user = getByName(userName);
        user.addPermission(permission);
        UserRepository.getInstance().save(user);
    }

    @Override
    public void removePermission(String userName, Permission permission) {
        SimpleUser user = getByName(userName);
        user.removePermission(permission);
        UserRepository.getInstance().save(user);
    }

    @Override
    public List<AuthUser> findAll() {
        return UserRepository.getInstance().findAll().parallelStream().map(simpleUser -> (AuthUser) simpleUser).collect(Collectors.toList());
    }
}
