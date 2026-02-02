package com.home.java.core;

import com.home.java.core.logging.Demo;
import com.home.java.core.logging.Log;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * DEMO: equals đúng nhưng hashCode SAI
 * <p>
 * 👉 Trả lời:
 * "Vì sao map.get(key) trả null dù equals = true?"
 */
public final class EqualsOnlyDemo implements Demo {

    private static final Logger log = Log.of(EqualsOnlyDemo.class);

    @Override
    public void run() {

        User u1 = new User("U01");
        User u2 = new User("U01");

        log.info("u1.equals(u2) = {}", u1.equals(u2));
        log.info("u1.hashCode = {}", u1.hashCode());
        log.info("u2.hashCode = {}", u2.hashCode());

        Map<User, String> map = new HashMap<>();
        map.put(u1, "Alice");

        log.info("map.get(u2) = {}", map.get(u2));
        log.info("=> FAIL vì hashCode khác bucket");
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

        // ❌ KHÔNG override hashCode
    }
}
