package com.home.java.core.object.model;

public final class MutableBox {
    private int value;

    public MutableBox(int value) {
        this.value = value;
    }

    public void set(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MutableBox{value=" + value + "}";
    }
}

