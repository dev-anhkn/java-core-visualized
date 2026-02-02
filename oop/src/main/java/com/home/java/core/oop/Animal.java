package com.home.java.core.oop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Animal {

    protected static final Logger log = LoggerFactory.getLogger(Animal.class);

    public String type = "ANIMAL";

    public Animal() {
        log.info("[CTOR] Animal()");
    }

    public void speak() {
        log.info("[METHOD] Animal.speak()");
    }

    public void eat(String food) {
        log.info("[METHOD] Animal.eat(String) -> {}", food);
    }
}

