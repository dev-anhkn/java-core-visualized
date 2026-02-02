package com.home.java.core.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Log {

    private Log() {
    }

    /**
     * Lấy logger theo class (chuẩn slf4j)
     */
    public static Logger of(Class<?> type) {
        return LoggerFactory.getLogger(type);
    }

    /**
     * In ra identity thật của object (không override được)
     */
    public static String oid(Object o) {
        if (o == null) return "null";
        return o.getClass().getSimpleName() + "@" + Integer.toHexString(System.identityHashCode(o));
    }

    /**
     * Thread hiện tại – bắt buộc cho Java Core
     */
    public static String thread() {
        return Thread.currentThread().getName();
    }
}
