package com.home.java.core.oop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InheritancePolymorphismDemo {

    private static final Logger log = LoggerFactory.getLogger(InheritancePolymorphismDemo.class);

    public static void main(String[] args) {

        log.info("=== CREATE OBJECT ===");
        Animal animal = new Dog();

        log.info("=== RUNTIME TYPE ===");
        log.info("animal.getClass() = {}", animal.getClass().getName());

        log.info("=== METHOD (RUNTIME POLYMORPHISM) ===");
        animal.speak();

        log.info("=== FIELD (NO POLYMORPHISM) ===");
        log.info("animal.type = {}", animal.type);

        log.info("=== OVERLOAD vs OVERRIDE ===");
        animal.eat("bone");

        Dog dog = (Dog) animal;
        dog.eat(5);

        log.info("=== SUMMARY ===");
        log.info("Method -> runtime binding");
        log.info("Field  -> compile-time binding");
        log.info("Overload -> compile-time");
        log.info("Override -> runtime");
        log.info("Constructor -> parent then child");
    }
}

// Overloading: Quyết định lúc Compile-time, dựa trên danh sách tham số, Kiểu dữ liệu tham số, Thứ tự tham số gợi nhớ - 1 tên những sẽ có nhiều hàm