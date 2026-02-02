package com.home.java.core.object.model.method;

import com.home.java.core.logging.Log;
import com.home.java.core.logging.LogFmt;
import com.home.java.core.object.model.Demo;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DEMO: COLLECTION MUTATION vs REASSIGN
 * <p>
 * ================= BÀI HỌC =================
 * <p>
 * 1. Collection (List, Map, Set) là OBJECT (mutable).
 * 2. Khi truyền collection vào method:
 * - Java copy GIÁ TRỊ của reference (pass-by-value).
 * 3. Mutate collection (add/remove/put):
 * - Thay đổi object trên heap
 * - Ảnh hưởng ra bên ngoài method.
 * 4. Reassign collection sang object mới:
 * - Chỉ ảnh hưởng reference LOCAL
 * - Không ảnh hưởng collection ban đầu.
 * <p>
 * ================= NÓI KHI PHỎNG VẤN =================
 * <p>
 * "Collection là mutable object.
 * Mutate nội dung thì ảnh hưởng bên ngoài, nhưng gán collection mới trong method thì không."
 */
public final class CollectionMutateDemo implements Demo {

    private static final Logger log = Log.of(CollectionMutateDemo.class);

    @Override
    public void run() {
        banner();

        listDemo();
        mapDemo();
    }

    /**
     * LIST DEMO
     */
    private void listDemo() {

        log.info("----- LIST DEMO -----");

        List<String> names = new ArrayList<>();
        names.add("Alice");
        names.add("Bob");

        log.info("run(): tạo List names");
        log.info("Stack run() giữ reference -> {}", LogFmt.ref("names", names));
        log.info("Heap List BAN ĐẦU -> {}", names);

        mutateList(names);

        log.info("Quay lại run() sau mutateList()");
        log.info("Heap List SAU MUTATE -> {}", names);

        reassignList(names);

        log.info("Quay lại run() sau reassignList()");
        log.info("Heap List VẪN GIỮ NGUYÊN -> {}", names);
    }

    private void mutateList(List<String> list) {

        log.info(LogFmt.enter("mutateList"));

        /*
         * STACK
         *
         * ┌────────────────────────────┐
         * │ stack frame mutateList()   │
         * │   list ──────────────────┐ │
         * └──────────────────────────│─┘
         * │ stack frame run()        │ │
         * │   names ─────────────────┘ │
         * └────────────────────────────┘
         *
         * HEAP
         * ┌─────────────────────┐
         * │ ["Alice", "Bob"]    │
         * └─────────────────────┘
         */

        log.info("Trước mutate -> {}", list);

        list.add("Charlie");
        list.remove("Alice");

        log.info("Sau mutate -> {}", list);
        log.info("Kết thúc mutateList(), stack frame sẽ bị huỷ");
    }

    private void reassignList(List<String> list) {

        log.info(LogFmt.enter("reassignList"));

        log.info("Trước reassign -> {}", list);

        list = new ArrayList<>();
        list.add("X");
        list.add("Y");

        log.info("Sau reassign (LOCAL) -> {}", list);
        log.info("Kết thúc reassignList(), stack frame sẽ bị huỷ");
    }

    /**
     * MAP DEMO
     */
    private void mapDemo() {

        log.info("----- MAP DEMO -----");

        Map<String, Integer> scores = new HashMap<>();
        scores.put("Math", 8);
        scores.put("English", 9);

        log.info("run(): tạo Map scores");
        log.info("Stack run() giữ reference -> {}", LogFmt.ref("scores", scores));
        log.info("Heap Map BAN ĐẦU -> {}", scores);

        mutateMap(scores);

        log.info("Quay lại run() sau mutateMap()");
        log.info("Heap Map SAU MUTATE -> {}", scores);

        reassignMap(scores);

        log.info("Quay lại run() sau reassignMap()");
        log.info("Heap Map VẪN GIỮ NGUYÊN -> {}", scores);
    }

    private void mutateMap(Map<String, Integer> map) {

        log.info(LogFmt.enter("mutateMap"));

        log.info("Trước mutate -> {}", map);

        map.put("Math", 10);
        map.remove("English");

        log.info("Sau mutate -> {}", map);
        log.info("Kết thúc mutateMap(), stack frame sẽ bị huỷ");
    }

    private void reassignMap(Map<String, Integer> map) {

        log.info(LogFmt.enter("reassignMap"));

        log.info("Trước reassign -> {}", map);

        map = new HashMap<>();
        map.put("Physics", 7);

        log.info("Sau reassign (LOCAL) -> {}", map);
        log.info("Kết thúc reassignMap(), stack frame sẽ bị huỷ");
    }

    private void banner() {
        log.info("================================================");
        log.info("DEMO: COLLECTION MUTATION vs REASSIGN");
        log.info("================================================");
    }
}
