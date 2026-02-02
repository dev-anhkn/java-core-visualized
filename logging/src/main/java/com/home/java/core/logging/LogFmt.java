package com.home.java.core.logging;

public final class LogFmt {

    private LogFmt() {
    }

    public static String enter(String action) {
        return "[ENTER] " + action;
    }

    public static String exit(String action) {
        return "[EXIT ] " + action;
    }

    public static String state(String key, Object value) {
        return key + "=" + value;
    }

    public static String ref(String name, Object o) {
        return name + "->" + Log.oid(o);
    }
}
