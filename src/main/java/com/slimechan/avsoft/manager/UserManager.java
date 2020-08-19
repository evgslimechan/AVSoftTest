package com.slimechan.avsoft.manager;

import com.slimechan.avsoft.entity.permission.Permission;
import com.slimechan.avsoft.entity.user.AuthUser;
import com.slimechan.avsoft.manager.impl.UserManagerImpl;

import java.util.List;

public interface UserManager {

    UserManager Instance = new UserManagerImpl();

    boolean exists(String username);

    AuthUser getByName(String username);

    AuthUser register(String username, String password);

    boolean auth(String username, String password);

    void addPermission(String userName, Permission permission);

    void removePermission(String userName, Permission permission);

    List<AuthUser> findAll();

}
