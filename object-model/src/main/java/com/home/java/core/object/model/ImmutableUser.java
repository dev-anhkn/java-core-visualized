package com.home.java.core.object.model;

import java.util.Date;

public record ImmutableUser(String name, Date createdAt) {

    public ImmutableUser(String name, Date createdAt) {
        this.name = name;
        // Defensive copy
        this.createdAt = new Date(createdAt.getTime());
    }

    @Override
    public Date createdAt() {
        // Defensive copy khi trả ra ngoài
        return new Date(createdAt.getTime());
    }

    @Override
    public String toString() {
        return "ImmutableUser{" + "name='" + name + '\'' + ", createdAt=" + createdAt + '}';
    }
}
