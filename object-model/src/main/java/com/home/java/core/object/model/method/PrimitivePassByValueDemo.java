package com.home.java.core.object.model.method;

import com.home.java.core.logging.Log;
import com.home.java.core.logging.LogFmt;
import com.home.java.core.object.model.Demo;
import org.slf4j.Logger;

/**
 * DEMO: PRIMITIVE PASS-BY-VALUE
 * <p>
 * ================= BÀI HỌC =================
 * <p>
 * 1. Java LUÔN pass-by-value.
 * 2. Với primitive (int, long, ...):
 * - Giá trị được COPY vào method được gọi.
 * 3. Method đang gọi và method được gọi KHÔNG chia sẻ cùng một biến.
 * <p>
 * ================= NÓI KHI PHỎNG VẤN =================
 * <p>
 * "Khi truyền primitive vào method,
 * Java copy giá trị, method bên trong chỉ làm việc với biến của chính nó."
 */
public final class PrimitivePassByValueDemo implements Demo {

    private static final Logger log = Log.of(PrimitivePassByValueDemo.class);

    @Override
    public void run() {
        banner();

        // Method đang gọi: run()
        int x = 10;

        log.info("Method đang gọi: khai báo x=10");
        log.info("Stack frame của run() -> x={}", x); // Stack frame = “một ngăn tạm thời trên bộ nhớ stack

        // Gọi method khác và truyền giá trị của x
        increase(x);

        log.info("Quay lại method run()");
        log.info("Stack frame của run() -> x={} (KHÔNG ĐỔI)", x);
    }

    /**
     * Đây là method ĐƯỢC GỌI.
     * Nó nhận 1 BẢN SAO của giá trị x.
     */
    private void increase(int x) {

        log.info(LogFmt.enter("increase"));

        /*
         * JVM tạo stack frame mới cho increase():
         *
         * STACK (thread main)
         *
         * ┌─────────────────────────────┐
         * │ stack frame increase()      │
         * │   x = 10   ← BẢN SAO         │
         * └─────────────────────────────┘
         * │ stack frame run()           │
         * │   x = 10   ← BIẾN GỐC        │
         * └─────────────────────────────┘
         */

        log.info("Stack frame của increase() -> x={}", x);

        // Chỉ thay đổi biến LOCAL của increase()
        x++;
        log.info("Sau khi tăng x trong increase() -> x={}", x);
        log.info("Kết thúc increase(), stack frame sẽ bị huỷ");
    }

    private void banner() {
        log.info("================================================");
        log.info("DEMO: PRIMITIVE PASS-BY-VALUE");
        log.info("================================================");
    }
}


/*
 * Biến bên ngoài → nằm trong stack frame của method đang gọi
 * Biến trong hàm → nằm trong stack frame của method được gọi
 * Hai stack frame tách biệt
 * Method kết thúc → stack frame bị xoá → mọi thay đổi biến mất
 * */