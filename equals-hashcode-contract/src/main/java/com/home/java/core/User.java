package com.home.java.core;

import java.util.Objects;

public class User {

    private final String id;

    User(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        // thuật toán băm
        return Objects.hashCode(id);
    }
}
