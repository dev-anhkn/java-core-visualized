package com.home.java.core.object.model.method;

import com.home.java.core.logging.Log;
import com.home.java.core.logging.LogFmt;
import com.home.java.core.object.model.Demo;
import org.slf4j.Logger;

/**
 * DEMO: STRING IMMUTABILITY & STRING POOL
 * <p>
 * ================= BÀI HỌC =================
 * <p>
 * 1. String là OBJECT nhưng IMMUTABLE.
 * 2. Mọi phép nối / thay đổi String:
 * - Tạo object String MỚI
 * - Không mutate object cũ.
 * 3. String literal được quản lý bởi STRING POOL.
 * 4. Khi truyền String vào method:
 * - Java copy GIÁ TRỊ reference (pass-by-value).
 * - Reassign reference trong method không ảnh hưởng bên ngoài.
 * <p>
 * ================= NÓI KHI PHỎNG VẤN =================
 * <p>
 * "String là immutable object.
 * Mọi thay đổi đều tạo instance mới, nên không thể mutate String trong method."
 */
public final class StringDemo implements Demo {

    private static final Logger log = Log.of(StringDemo.class);

    @Override
    public void run() {
        banner();

        String text = "Hello";

        log.info("run(): tạo String text = \"Hello\"");
        log.info("Stack run() giữ reference -> {}", LogFmt.ref("text", text));
        log.info("Heap/String Pool object BAN ĐẦU -> {}", text);

        modify(text);

        log.info("Quay lại run()");
        log.info("Stack run() vẫn giữ reference -> {}", LogFmt.ref("text", text));
        log.info("Heap/String Pool object VẪN GIỮ NGUYÊN -> {}", text);

        poolDemo();
    }

    /**
     * Method được gọi.
     * Nhận 1 BẢN SAO của reference text.
     * <p>
     * BÀI HỌC:
     * - String immutable
     * - Phép nối tạo object mới
     * - Reassign reference LOCAL
     */
    private void modify(String text) {

        log.info(LogFmt.enter("modify"));

        /*
         * STACK (khi vào modify)
         *
         * ┌────────────────────────────┐
         * │ stack frame modify()       │
         * │   text ──────────────────┐ │
         * └──────────────────────────│─┘
         * │ stack frame run()         │ │
         * │   text ──────────────────┘ │
         * └────────────────────────────┘
         *
         * STRING POOL / HEAP
         * ┌───────────────┐
         * │ "Hello"       │
         * └───────────────┘
         */

        log.info("Stack modify() giữ reference -> {}", LogFmt.ref("text", text));

        // Phép nối tạo object String MỚI
        text = text + " World";

        log.info("Sau khi text = text + \" World\"");
        log.info("Stack modify() giữ reference MỚI -> {}", LogFmt.ref("text", text));
        log.info("Heap/String Pool object MỚI -> {}", text);

        log.info("Kết thúc modify(), stack frame sẽ bị huỷ");
    }

    /**
     * Demo String Pool & ==
     */
    private void poolDemo() {

        log.info("----- STRING POOL DEMO -----");

        String a = "Java";
        String b = "Java";
        String c = new String("Java");

        log.info("a == b  -> {}", a == b); // true (string pool)
        log.info("a == c  -> {}", a == c); // false (new object)
        log.info("a.equals(c) -> {}", a.equals(c)); // true (value)
    }

    private void banner() {
        log.info("================================================");
        log.info("DEMO: STRING IMMUTABILITY & STRING POOL");
        log.info("================================================");
    }
}
