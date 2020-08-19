package com.slimechan.avsoft.entity.permission;

import com.slimechan.avsoft.entity.user.impl.SimpleUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Getter
    @GeneratedValue
    @Id
    private long id;
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @OneToMany(targetEntity = SimpleUser.class)
    private List<SimpleUser> users;

    @Setter
    @Type(type = "com.slimechan.avsoft.dao.type.PermissionsArray")
    private Permission[] permissions;

    public List<Permission> getPermissions() {
        return Arrays.asList(permissions);
    }

}
