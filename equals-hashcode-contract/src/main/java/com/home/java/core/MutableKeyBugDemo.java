package com.home.java.core;

import com.home.java.core.logging.Demo;
import com.home.java.core.logging.Log;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * DEMO: Mutable key làm HashMap lỗi
 * <p>
 * 👉 Vì sao key biến mất sau khi put?
 */
public final class MutableKeyBugDemo implements Demo {

    private static final Logger log = Log.of(MutableKeyBugDemo.class);

    @Override
    public void run() {

        Map<User, String> map = new HashMap<>();

        User user = new User("U04");
        map.put(user, "DATA");

        log.info("Before mutate: map.get(user) = {}", map.get(user));

        user.setId("U99"); // ☠️ mutate key

        log.info("After mutate: map.get(user) = {}", map.get(user));
        map.forEach((k, v) -> log.info("{} : {}", k, v));
        log.info("=> FAIL vì hashCode thay đổi");
    }

    private static final class User {
        private String id;

        User(String id) {
            this.id = id;
        }

        void setId(String id) {
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

