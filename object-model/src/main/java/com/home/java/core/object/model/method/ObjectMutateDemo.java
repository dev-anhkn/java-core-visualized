package com.home.java.core.object.model.method;

import com.home.java.core.logging.Log;
import com.home.java.core.logging.LogFmt;
import com.home.java.core.object.model.Demo;
import com.home.java.core.object.model.Person;
import org.slf4j.Logger;

/**
 * DEMO: OBJECT MUTATE
 * <p>
 * ================= BÀI HỌC =================
 * <p>
 * 1. Java vẫn pass-by-value.
 * 2. Khi truyền object vào method:
 * - Java copy GIÁ TRỊ của reference.
 * 3. Reference nằm trên stack.
 * 4. Object thật nằm trên heap.
 * <p>
 * Khi method SỬA TRẠNG THÁI của object trên heap:
 * - Heap thay đổi
 * - Mọi reference cùng trỏ tới object đó đều thấy thay đổi
 * <p>
 * ================= NÓI KHI PHỎNG VẤN =================
 * <p>
 * "Java không truyền object.
 * Java truyền bản sao của reference.
 * Khi mutate object trên heap,
 * thay đổi được nhìn thấy ở bên ngoài."
 */
public final class ObjectMutateDemo implements Demo {

    private static final Logger log = Log.of(ObjectMutateDemo.class);

    @Override
    public void run() {
        banner();

        // Method đang gọi: run()
        Person person = new Person("Alice", 20);

        log.info("run(): tạo object Person");
        log.info("Stack run() giữ reference -> {}", LogFmt.ref("person", person));
        log.info("Heap object BAN ĐẦU -> {}", person);

        // Truyền reference vào method khác
        mutateObject(person);

        log.info("Quay lại run()");
        log.info("Stack run() vẫn giữ reference -> {}", LogFmt.ref("person", person));
        log.info("Heap object SAU KHI BỊ MUTATE -> {}", person);
    }

    /**
     * Method được gọi.
     * Nhận 1 BẢN SAO của reference person.
     * <p>
     * BÀI HỌC:
     * - Reference mới vẫn trỏ tới CÙNG object trên heap.
     * - Mutate object → heap đổi.
     */
    private void mutateObject(Person person) {

        log.info(LogFmt.enter("mutateObject"));

        /*
         * STACK
         * ┌────────────────────────────┐
         * │ stack frame mutateObject() │
         * │   person ────────────────┐ │
         * └──────────────────────────│─┘
         * │ stack frame run()        │ │
         * │   person ────────────────┘ │
         * └────────────────────────────┘
         *
         * HEAP
         * ┌─────────────────────────────┐
         * │ Person{name="Alice", age=20}│
         * └─────────────────────────────┘
         */

        log.info("Stack mutateObject() giữ reference -> {}", LogFmt.ref("person", person));
        log.info("Sắp sửa TRẠNG THÁI object trên heap");

        // MUTATE object (sửa dữ liệu bên trong object)
        person.birthday();

        log.info("Heap object SAU KHI MUTATE -> {}", person);
        log.info("Kết thúc mutateObject(), stack frame sẽ bị huỷ");
    }

    private void banner() {
        log.info("================================================");
        log.info("DEMO: OBJECT MUTATE");
        log.info("================================================");
    }
}


/*
 * Java copy giá trị của reference khi truyền object vào hàm.
 * Cả bên trong và bên ngoài hàm đều trỏ tới cùng một object trên heap,
 * nên khi hàm sửa trạng thái của object trên heap, thay đổi đó được nhìn thấy ở bên ngoài.
 * */