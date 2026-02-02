package com.home.java.core.object.model.method;

import com.home.java.core.logging.Log;
import com.home.java.core.logging.LogFmt;
import com.home.java.core.object.model.Demo;
import com.home.java.core.object.model.ImmutableUser;
import org.slf4j.Logger;

import java.util.Date;

public final class ImmutabilityDemo implements Demo {

    private static final Logger log = Log.of(ImmutabilityDemo.class);

    @Override
    public void run() {
        banner();

        Date now = new Date();
        ImmutableUser user = new ImmutableUser("Alice", now);

        log.info("run(): tạo ImmutableUser");
        log.info("Stack run() giữ reference -> {}", LogFmt.ref("user", user));
        log.info("Heap object BAN ĐẦU -> {}", user);

        mutateExternalDate(now);

        log.info("Sau khi external Date bị mutate");
        log.info("ImmutableUser VẪN GIỮ NGUYÊN -> {}", user);

        readFromMultipleThreads(user);
    }

    /**
     * Thay đổi Date bên ngoài
     * nhưng KHÔNG ảnh hưởng ImmutableUser
     */
    private void mutateExternalDate(Date date) {
        log.info(LogFmt.enter("mutateExternalDate"));

        date.setTime(0);

        log.info("External Date đã bị mutate -> {}", date);
        log.info(LogFmt.exit("mutateExternalDate"));
    }

    /**
     * Demo nhiều thread đọc cùng immutable object
     * KHÔNG cần synchronized
     */
    private void readFromMultipleThreads(ImmutableUser user) {

        log.info("Bắt đầu đọc ImmutableUser từ nhiều thread");

        Runnable task = () -> log.info(
                "{} đọc user -> {} | hash={}",
                Thread.currentThread().getName(),
                user,
                System.identityHashCode(user)
        );

        new Thread(task, "worker-1").start();
        new Thread(task, "worker-2").start();
        new Thread(task, "worker-3").start();
    }

    private void banner() {
        log.info("================================================");
        log.info("DEMO: IMMUTABILITY – OBJECT BẤT BIẾN");
        log.info("================================================");
    }
}

// Object immutable thread-safe vì nó không thể bị thay đổi nên không sợ race condition.