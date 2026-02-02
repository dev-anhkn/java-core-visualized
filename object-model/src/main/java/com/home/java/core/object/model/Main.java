package com.home.java.core.object.model;

import com.home.java.core.logging.Log;
import com.home.java.core.object.model.method.ArrayMutateDemo;
import com.home.java.core.object.model.method.BoxDemo;
import com.home.java.core.object.model.method.CollectionMutateDemo;
import com.home.java.core.object.model.method.ImmutabilityDemo;
import com.home.java.core.object.model.method.ObjectMutateDemo;
import com.home.java.core.object.model.method.PrimitivePassByValueDemo;
import com.home.java.core.object.model.method.ReassignReferenceDemo;
import com.home.java.core.object.model.method.StringDemo;
import com.home.java.core.object.model.method.WrapperDemo;
import org.slf4j.Logger;

import java.util.List;

public class Main {

    private Main() {
    }

    private static final Logger log = Log.of(Main.class);

    static void main() {

        List<Demo> demos = List.of(
//                new PrimitivePassByValueDemo(),
//                new ObjectMutateDemo()
//                new ReassignReferenceDemo()
//                new BoxDemo()
//                new WrapperDemo()
//                new StringDemo()
//                new ArrayMutateDemo()
//                new CollectionMutateDemo()
                new ImmutabilityDemo()
        );

        for (Demo demo : demos) {
            runDemo(demo);
        }
    }

    private static void runDemo(Demo demo) {
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
