package com.home.java.core.logging;

import org.slf4j.Logger;

public class Utils {

    private static final Logger log = Log.of(Utils.class);


    private Utils() {}

    public static void runDemo(Demo demo) {
        log.info("");
        log.info("------------------------------------------------");
        log.info("BẮT ĐẦU DEMO: {}", demo.getClass().getSimpleName());
        log.info("------------------------------------------------");

        demo.run();

        log.info("------------------------------------------------");
        log.info("KẾT THÚC DEMO: {}", demo.getClass().getSimpleName());
        log.info("------------------------------------------------");
    }
}
