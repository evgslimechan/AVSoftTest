package com.slimechan.avsoft.entity.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class Permission {

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String value;
    @Getter
    @Setter
    private int weight;

    public static Permission toPermission(String value) {
        String[] data = value.split(":");
        return new Permission(data[0], data[1], Integer.parseInt(data[2]));
    }

    @Override
    public String toString() {
        return name + ":" + value + ":" + weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Permission that = (Permission) o;

        if (weight != that.weight) return false;
        if (name != null ? !name.equalsIgnoreCase(that.name) : that.name != null) return false;
        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + weight;
        return result;
    }
}
