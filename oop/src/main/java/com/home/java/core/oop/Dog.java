package com.home.java.core.oop;

public class Dog extends Animal {

    public String type = "DOG";

    public Dog() {
        log.info("[CTOR] Dog()");
    }

    @Override
    public void speak() {
        log.info("[METHOD] Dog.speak()");
    }

    /*
     * ===================== OVERLOAD (COMPILE-TIME) =====================
     *
     * Overloading KHÔNG phải runtime polymorphism.
     * Method được chọn tại compile time.
     *
     * Thực tế production:
     * - Hạn chế dùng overload cho nghiệp vụ
     * - Ưu tiên method name rõ nghĩa hoặc Command object
     */
    public void eat(int amount) {
        log.info("[METHOD] Dog.eat(int) -> {}", amount);
    }
}

