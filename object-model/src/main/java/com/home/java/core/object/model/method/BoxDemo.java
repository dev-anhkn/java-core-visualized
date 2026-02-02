package com.home.java.core.object.model.method;

import com.home.java.core.logging.Log;
import com.home.java.core.logging.LogFmt;
import com.home.java.core.object.model.Demo;
import org.slf4j.Logger;

/**
 * DEMO: BOXING / UNBOXING & IMMUTABILITY
 * <p>
 * ================= BÀI HỌC =================
 * <p>
 * 1. Wrapper class (Integer, Long, ...) là OBJECT.
 * 2. Nhưng wrapper là IMMUTABLE.
 * 3. Khi truyền wrapper vào method:
 * - Java copy GIÁ TRỊ của reference (pass-by-value).
 * 4. Khi thực hiện phép toán (num += 1):
 * - Unboxing → primitive
 * - Tạo object wrapper MỚI
 * - Reassign reference LOCAL
 * <p>
 * ================= NÓI KHI PHỎNG VẤN =================
 * <p>
 * "Wrapper là object nhưng immutable.
 * Mọi phép toán đều tạo object mới, nên thay đổi wrapper trong method không ảnh hưởng biến bên ngoài."
 */
public final class BoxDemo implements Demo {

    private static final Logger log = Log.of(BoxDemo.class);

    @Override
    public void run() {
        banner();

        Integer num = 10;

        log.info("run(): tạo Integer num = 10");
        log.info("Stack run() giữ reference -> {}", LogFmt.ref("num", num));
        log.info("Heap object BAN ĐẦU -> {}", num);

        increase(num);

        log.info("Quay lại run()");
        log.info("Stack run() vẫn giữ reference -> {}", LogFmt.ref("num", num));
        log.info("Heap object VẪN GIỮ NGUYÊN -> {}", num);
    }

    /**
     * Method được gọi.
     * Nhận 1 BẢN SAO của reference num.
     * <p>
     * BÀI HỌC:
     * - Wrapper là immutable
     * - Phép toán tạo object mới
     * - Chỉ reference LOCAL bị thay đổi
     */
    private void increase(Integer num) {

        log.info(LogFmt.enter("increase"));

        /*
         * STACK (trước khi increase)
         *
         * ┌────────────────────────────┐
         * │ stack frame increase()     │
         * │   num ───────────────────┐ │
         * └──────────────────────────│─┘
         * │ stack frame run()        │ │
         * │   num ───────────────────┘ │
         * └────────────────────────────┘
         *
         * HEAP
         * ┌───────────────┐
         * │ Integer(10)   │
         * └───────────────┘
         */

        log.info("Stack increase() giữ reference -> {}", LogFmt.ref("num", num));

        // Phép toán: unboxing → primitive → boxing → object mới
        num += 1;

        log.info("Sau khi num += 1");
        log.info("Stack increase() giữ reference MỚI -> {}", LogFmt.ref("num", num));
        log.info("Heap object MỚI -> {}", num);

        log.info("Kết thúc increase(), stack frame sẽ bị huỷ");
    }

    private void banner() {
        log.info("================================================");
        log.info("DEMO: BOXING / UNBOXING & IMMUTABILITY");
        log.info("================================================");
    }
}

// Wrapper là object nhưng immutable, nên mọi phép toán đều tạo instance mới.