package com.slimechan.avsoft.entity.user.impl;

import com.slimechan.avsoft.entity.permission.Permission;
import com.slimechan.avsoft.entity.permission.Role;
import com.slimechan.avsoft.entity.user.AuthUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class SimpleUser implements AuthUser {

    @Getter
    @GeneratedValue
    @Id
    private int id;

    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    @ManyToOne(targetEntity = Role.class)
    private Role role;

    @Setter
    @Type(type = "com.slimechan.avsoft.dao.type.PermissionsArray")
    private Permission[] permissions = new Permission[0];

    public List<Permission> getPermissions() {
        Set<Permission> set = new LinkedHashSet<>();
        set.addAll(role.getPermissions());
        set.addAll(Arrays.asList(permissions));
        return new ArrayList<>(set);
    }

    public String getName() {
        return getUsername();
    }

    public void addPermission(Permission permission) {
        Set<Permission> permissions = new HashSet<>(Arrays.asList(this.permissions));
        permissions.add(permission);
        setPermissions(permissions.toArray(new Permission[0]));
    }

    public void removePermission(Permission permission) {
        Set<Permission> permissions = new HashSet<>(Arrays.asList(this.permissions));
        permissions.remove(permission);
        setPermissions(permissions.toArray(new Permission[0]));
    }

}
