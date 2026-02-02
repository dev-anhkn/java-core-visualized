package com.home.java.core.object.model.method;

import com.home.java.core.logging.Log;
import com.home.java.core.logging.LogFmt;
import com.home.java.core.object.model.Demo;
import org.slf4j.Logger;

/**
 * DEMO: ARRAY MUTATION vs REASSIGN
 * <p>
 * ================= BÀI HỌC =================
 * <p>
 * 1. Array trong Java là OBJECT (nằm trên heap).
 * 2. Khi truyền array vào method:
 * - Java copy GIÁ TRỊ của reference (pass-by-value).
 * 3. Thay đổi PHẦN TỬ của array:
 * - Mutate object trên heap
 * - Ảnh hưởng ra bên ngoài method.
 * 4. Gán array sang object mới:
 * - Chỉ thay đổi reference LOCAL
 * - Không ảnh hưởng array ban đầu.
 * <p>
 * ================= NÓI KHI PHỎNG VẤN =================
 * <p>
 * "Array là object mutable. Mutate phần tử sẽ ảnh hưởng bên ngoài, nhưng reassign array trong method thì không."
 */
public final class ArrayMutateDemo implements Demo {

    private static final Logger log = Log.of(ArrayMutateDemo.class);

    @Override
    public void run() {
        banner();

        int[] numbers = {1, 2, 3};

        log.info("run(): tạo array numbers = {1,2,3}");
        log.info("Stack run() giữ reference -> {}", LogFmt.ref("numbers", numbers));
        log.info("Heap array BAN ĐẦU -> {}", dump(numbers));

        mutate(numbers);

        log.info("Quay lại run() sau mutate()");
        log.info("Heap array SAU MUTATE -> {}", dump(numbers));

        reassign(numbers);

        log.info("Quay lại run() sau reassign()");
        log.info("Heap array VẪN GIỮ NGUYÊN -> {}", dump(numbers));
    }

    /**
     * Mutate phần tử array
     * → Ảnh hưởng object trên heap
     */
    private void mutate(int[] numbers) {

        log.info(LogFmt.enter("mutate"));

        /*
         * STACK
         *
         * ┌────────────────────────────┐
         * │ stack frame mutate()       │
         * │   numbers ───────────────┐ │
         * └──────────────────────────│─┘
         * │ stack frame run()         │ │
         * │   numbers ───────────────┘ │
         * └────────────────────────────┘
         *
         * HEAP
         * ┌───────────────┐
         * │ [1, 2, 3]     │
         * └───────────────┘
         */

        log.info("Trước mutate -> {}", dump(numbers));

        numbers[0] = 99;
        numbers[1] = 88;

        log.info("Sau mutate -> {}", dump(numbers));
        log.info("Kết thúc mutate(), stack frame sẽ bị huỷ");
    }

    /**
     * Reassign array sang object mới
     * → Chỉ ảnh hưởng reference LOCAL
     */
    private void reassign(int[] numbers) {

        log.info(LogFmt.enter("reassign"));

        log.info("Trước reassign -> {}", dump(numbers));

        numbers = new int[]{7, 7, 7};

        log.info("Sau reassign (LOCAL) -> {}", dump(numbers));
        log.info("Kết thúc reassign(), stack frame sẽ bị huỷ");
    }

    private String dump(int[] arr) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    private void banner() {
        log.info("================================================");
        log.info("DEMO: ARRAY MUTATION vs REASSIGN");
        log.info("================================================");
    }
}
