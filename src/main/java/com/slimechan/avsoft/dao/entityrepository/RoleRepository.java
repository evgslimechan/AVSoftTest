package com.slimechan.avsoft.dao.entityrepository;

import com.slimechan.avsoft.dao.HibernateRepository;
import com.slimechan.avsoft.entity.permission.Permission;
import com.slimechan.avsoft.entity.permission.Role;
import com.slimechan.avsoft.manager.PropertiesManager;

import java.util.List;

public class RoleRepository extends HibernateRepository<Role, Long> {

    public static final String ADMIN_GROUP_NAME = PropertiesManager.Instance.getProperty("role.default.admin");
    public static final String DEFAULT_GROUP_NAME = PropertiesManager.Instance.getProperty("role.default.user");

    private static RoleRepository repository;

    protected RoleRepository() {
        super(Long.class, Role.class);

        loadDefaultPermissions(DEFAULT_GROUP_NAME);
        loadDefaultPermissions(ADMIN_GROUP_NAME);

    }

    public static RoleRepository getInstance() {
        if (repository == null) repository = new RoleRepository();
        return repository;
    }

    private void loadDefaultPermissions(String roleName) {
        String[] stringPermissions = PropertiesManager.Instance.getProperty("role.default." + roleName + ".permissions").split(",");
        if (stringPermissions == null) return;
        Permission[] permissions = new Permission[stringPermissions.length];
        for (int i = 0; i < stringPermissions.length; i++) {
            permissions[i] = Permission.toPermission(stringPermissions[i]);
        }
        Role default_example = new Role();
        default_example.setName(roleName);

        if (findByExample(default_example).size() == 0) {
            default_example.setPermissions(permissions);
            save(default_example);
        }
    }

    public Role findByName(String roleName) {
        Role example = new Role();
        example.setName(roleName);
        List<Role> registered = findByExample(example);
        if (registered.size() > 0) return registered.get(0);
        else return null;
    }
}
