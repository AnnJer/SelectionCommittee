package com.kpi.javaee.servlet.entities;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {


    ADMINISTRATOR("admin"),
    ENROLLEE("enrollee");

    private String name;



    Role(String name) {
        this.name = name;
    }


    @Override
    public String getAuthority() {
        return name;
    }

    public static Role parseFromString(String name) {
        for (Role role: values()) {
            if (role.name.equals(name)) {
                return role;
            }
        }

        throw new IllegalArgumentException("Invalid user role " + name);
    }

}