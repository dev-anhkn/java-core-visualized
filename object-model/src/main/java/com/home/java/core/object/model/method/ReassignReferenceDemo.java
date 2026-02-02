package com.home.java.core.object.model.method;

import com.home.java.core.logging.Log;
import com.home.java.core.logging.LogFmt;
import com.home.java.core.object.model.Demo;
import com.home.java.core.object.model.Person;
import org.slf4j.Logger;

/**
 * DEMO: REASSIGN REFERENCE
 * <p>
 * ================= BÀI HỌC =================
 * <p>
 * 1. Java vẫn pass-by-value.
 * 2. Khi truyền object vào method:
 * - Java copy GIÁ TRỊ của reference.
 * 3. Khi gán reference sang object mới trong method:
 * - Chỉ reference LOCAL bị thay đổi.
 * - Object ban đầu trên heap KHÔNG bị ảnh hưởng.
 * <p>
 * ================= NÓI KHI PHỎNG VẤN =================
 * <p>
 * "Gán lại reference trong hàm
 * không làm object bên ngoài thay đổi,
 * vì Java không truyền reference theo kiểu pass-by-reference."
 */
public final class ReassignReferenceDemo implements Demo {

    private static final Logger log = Log.of(ReassignReferenceDemo.class);

    @Override
    public void run() {
        banner();

        // Method đang gọi: run()
        Person person = new Person("Alice", 20);

        log.info("run(): tạo object Person");
        log.info("Stack run() giữ reference -> {}", LogFmt.ref("person", person));
        log.info("Heap object BAN ĐẦU -> {}", person);

        // Truyền reference vào method khác
        reassign(person);

        log.info("Quay lại run()");
        log.info("Stack run() vẫn giữ reference -> {}", LogFmt.ref("person", person));
        log.info("Heap object VẪN GIỮ NGUYÊN -> {}", person);
    }

    /**
     * Method được gọi.
     * Nhận 1 BẢN SAO của reference person.
     * <p>
     * BÀI HỌC:
     * - Gán reference sang object mới
     * - Chỉ ảnh hưởng reference LOCAL
     */
    private void reassign(Person person) {

        log.info(LogFmt.enter("reassign"));

        /*
         * STACK (trước khi reassign)
         *
         * ┌────────────────────────────┐
         * │ stack frame reassign()     │
         * │   person ────────────────┐ │
         * └──────────────────────────│─┘
         * │ stack frame run()         │ │
         * │   person ────────────────┘ │
         * └────────────────────────────┘
         *
         * HEAP
         * ┌─────────────────────────────┐
         * │ Person{name="Alice", age=20}│
         * └─────────────────────────────┘
         */

        log.info("Stack reassign() giữ reference -> {}", LogFmt.ref("person", person));

        // GÁN REFERENCE sang object mới
        person = new Person("Bob", 99);

        log.info("Sau khi REASSIGN reference");
        log.info("Stack reassign() giữ reference MỚI -> {}", LogFmt.ref("person", person));
        log.info("Heap object MỚI -> {}", person);

        // Mutate object MỚI (chỉ để chứng minh object cũ không liên quan)
        person.birthday();
        log.info("Heap object MỚI sau khi mutate -> {}", person);

        log.info("Kết thúc reassign(), stack frame sẽ bị huỷ");
    }

    private void banner() {
        log.info("================================================");
        log.info("DEMO: REASSIGN REFERENCE");
        log.info("================================================");
    }
}

