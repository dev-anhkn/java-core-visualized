package com.home.java.core.logging;

import org.slf4j.Logger;

public class Utils {

    private static final String HYPHENS = "------------------------------------------------";
    private static final Logger log = Log.of(Utils.class);


    private Utils() {
    }

    public static void runDemo(Demo demo) {
        log.info("");
        log.info(HYPHENS);
        log.info("BẮT ĐẦU DEMO: {}", demo.getClass().getSimpleName());
        log.info(HYPHENS);

        demo.run();

        log.info(HYPHENS);
        log.info("KẾT THÚC DEMO: {}", demo.getClass().getSimpleName());
        log.info(HYPHENS);
    }
}
