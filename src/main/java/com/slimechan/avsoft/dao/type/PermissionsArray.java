package com.slimechan.avsoft.dao.type;

import com.slimechan.avsoft.entity.permission.Permission;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class PermissionsArray implements UserType {

    private static final int[] SQL_TYPES = new int[]{Types.CLOB};

    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    @Override
    public Class returnedClass() {
        return Permission[].class;
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        return o == o1;
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SharedSessionContractImplementor sharedSessionContractImplementor, Object owner) throws HibernateException, SQLException {

        String value = resultSet.getString(names[0]);

        if (value == null) {
            return null;
        } else {
            String[] stringPermissions = value.split("\\|");
            Permission[] permissions = new Permission[stringPermissions.length];

            for (int i = 0; i < stringPermissions.length; i++) {
                if (stringPermissions[i].isEmpty()) continue;
                String[] data = stringPermissions[i].split(":");
                permissions[i] = new Permission(data[0], data[1], Integer.parseInt(data[2]));
            }

            return Arrays.stream(permissions).filter(Objects::nonNull).collect(Collectors.toList()).toArray(new Permission[0]);
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {

        if (value == null) {
            preparedStatement.setString(index, "");
        } else {
            Permission[] array = (Permission[]) value;
            String[] copy = new String[array.length];
            for (int i = 0; i < array.length; i++) {
                copy[i] = array[i].getName() + ":" + array[i].getValue() + ":" + array[i].getWeight();
            }
            preparedStatement.setString(index, String.join("|", copy));
        }

    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        return o;
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }
}
