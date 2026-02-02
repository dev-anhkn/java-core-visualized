package com.home.java.core;

import com.home.java.core.logging.Demo;
import com.home.java.core.logging.Log;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * DEMO: equals & hashCode đúng contract
 *
 * 👉 Map hoạt động đúng
 */
public final class EqualsHashCodeDemo implements Demo {

    private static final Logger log = Log.of(EqualsHashCodeDemo.class);

    @Override
    public void run() {

        User u1 = new User("U02");
        User u2 = new User("U02");

        log.info("u1.equals(u2) = {}", u1.equals(u2));
        log.info("u1.hashCode = {}", u1.hashCode());
        log.info("u2.hashCode = {}", u2.hashCode());

        Map<User, String> map = new HashMap<>();
        map.put(u1, "Bob");

        log.info("map.get(u2) = {}", map.get(u2));
        log.info("=> OK vì cùng bucket + equals true");
    }
}
