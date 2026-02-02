package com.home.java.core.object.model.method;

import com.home.java.core.logging.Log;
import com.home.java.core.logging.LogFmt;
import com.home.java.core.object.model.Demo;
import org.slf4j.Logger;

/**
 * DEMO: WRAPPER IMMUTABILITY
 * <p>
 * ================= BÀI HỌC =================
 * <p>
 * 1. Wrapper class (Integer, Long, Double, ...) là OBJECT.
 * 2. Nhưng wrapper là IMMUTABLE.
 * 3. Không thể mutate trạng thái bên trong wrapper.
 * 4. Mọi "thay đổi" thực chất là:
 * - Unboxing → primitive
 * - Tạo wrapper object MỚI
 * - Reassign reference LOCAL
 * <p>
 * ================= NÓI KHI PHỎNG VẤN =================
 * <p>
 * "Wrapper là immutable object.
 * Không thể thay đổi giá trị bên trong, mọi phép toán đều tạo object mới, nên không ảnh hưởng biến bên ngoài method."
 */
public final class WrapperDemo implements Demo {

    private static final Logger log = Log.of(WrapperDemo.class);

    @Override
    public void run() {
        banner();

        Integer value = 100;

        log.info("run(): tạo Integer value = 100");
        log.info("Stack run() giữ reference -> {}", LogFmt.ref("value", value));
        log.info("Heap object BAN ĐẦU -> {}", value);

        tryMutate(value);

        log.info("Quay lại run()");
        log.info("Stack run() vẫn giữ reference -> {}", LogFmt.ref("value", value));
        log.info("Heap object VẪN GIỮ NGUYÊN -> {}", value);
    }

    /**
     * Method được gọi.
     * Nhận 1 BẢN SAO của reference value.
     * <p>
     * BÀI HỌC:
     * - Wrapper không có setter
     * - Không thể mutate
     * - Chỉ có thể tạo object mới
     */
    private void tryMutate(Integer value) {

        log.info(LogFmt.enter("tryMutate"));

        /*
         * STACK (khi vào tryMutate)
         *
         * ┌────────────────────────────┐
         * │ stack frame tryMutate()    │
         * │   value ─────────────────┐ │
         * └──────────────────────────│─┘
         * │ stack frame run()        │ │
         * │   value ─────────────────┘ │
         * └────────────────────────────┘
         *
         * HEAP
         * ┌───────────────┐
         * │ Integer(100)  │
         * └───────────────┘
         */

        log.info("Stack tryMutate() giữ reference -> {}", LogFmt.ref("value", value));

        // Không có setter, không thể mutate
        // value.set(200); // ❌ compile error

        // Phép toán tạo object mới
        value = value + 50;

        log.info("Sau khi value = value + 50");
        log.info("Stack tryMutate() giữ reference MỚI -> {}", LogFmt.ref("value", value));
        log.info("Heap object MỚI -> {}", value);

        log.info("Kết thúc tryMutate(), stack frame sẽ bị huỷ");
    }

    private void banner() {
        log.info("================================================");
        log.info("DEMO: WRAPPER IMMUTABILITY");
        log.info("================================================");
    }
}

