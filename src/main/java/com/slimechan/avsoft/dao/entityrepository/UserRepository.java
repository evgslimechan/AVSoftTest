package com.slimechan.avsoft.dao.entityrepository;

import com.slimechan.avsoft.dao.HibernateRepository;
import com.slimechan.avsoft.entity.user.impl.SimpleUser;
import com.slimechan.avsoft.util.PasswordEncoder;

public class UserRepository extends HibernateRepository<SimpleUser, Integer> {

    private static UserRepository repository;

    protected UserRepository() {
        super(Integer.class, SimpleUser.class);

        SimpleUser admin = new SimpleUser();
        admin.setUsername("admin");
        admin.setRole(RoleRepository.getInstance().findByName(RoleRepository.ADMIN_GROUP_NAME));

        if (findByExample(admin).size() == 0) {
            admin.setPassword(PasswordEncoder.hash("admin"));
            save(admin);
        }
    }

    public static UserRepository getInstance() {
        if (repository == null) repository = new UserRepository();
        return repository;
    }
}
