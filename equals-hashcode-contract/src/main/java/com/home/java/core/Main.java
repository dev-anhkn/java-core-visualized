package com.home.java.core;

import com.home.java.core.logging.Demo;
import com.home.java.core.logging.Utils;

import java.util.List;

public class Main {


    static void main() {
        List<Demo> demos = List.of(
//                new EqualsHashCodeDemo(),
//                new EqualsOnlyDemo()
//                new HashMapOverwriteDemo()
                new MutableKeyBugDemo()
        );

        for (Demo demo : demos) {
            Utils.runDemo(demo);
        }
    }


}
