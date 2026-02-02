package com.home.java.core;

import com.home.java.core.logging.Demo;
import com.home.java.core.logging.Log;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * DEMO: Overwrite key trong HashMap
 * <p>
 * 👉 Vì sao put 2 lần mà size = 1?
 */
public final class HashMapOverwriteDemo implements Demo {

    private static final Logger log = Log.of(HashMapOverwriteDemo.class);

    @Override
    public void run() {

        Map<User, String> map = new HashMap<>();

        User u1 = new User("U03");
        User u2 = new User("U03");

        map.put(u1, "First");
        map.put(u2, "Second");

        log.info("map.size = {}", map.size());
        log.info("map.get(u1) = {}", map.get(u1));

        log.info("=> equals = true → overwrite value");
    }

    static final class User {
        private final String id;

        User(String id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof User)) return false;
            User user = (User) o;
            return Objects.equals(id, user.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}

