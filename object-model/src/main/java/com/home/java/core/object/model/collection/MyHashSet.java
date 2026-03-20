package com.home.java.core.object.model.collection;

import java.util.Objects;

public class MyHashSet<E> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private Node<E>[] table;
    private int size;
    private int threshold;

    @SuppressWarnings("unchecked")
    public MyHashSet() {
        this.table = (Node<E>[]) new Node[DEFAULT_CAPACITY];
        this.threshold = (int) (DEFAULT_CAPACITY * LOAD_FACTOR);
    }

    public boolean add(E value) {
        if (size + 1 > threshold) {
            resize();
        }

        int index = indexFor(value, table.length);
        Node<E> current = table[index];

        // check trùng
        while (current != null) {
            if (Objects.equals(current.value, value)) {
                return false;
            }
            current = current.next;
        }

        // thêm đầu danh sách
        table[index] = new Node<>(value, table[index]);
        size++;
        return true;
    }

    public boolean contains(E value) {
        int index = indexFor(value, table.length);
        Node<E> current = table[index];

        while (current != null) {
            if (Objects.equals(current.value, value)) {
                return true;
            }
            current = current.next;
        }

        return false;
    }

    public boolean remove(E value) {
        int index = indexFor(value, table.length);
        Node<E> current = table[index];
        Node<E> prev = null;

        while (current != null) {
            if (Objects.equals(current.value, value)) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }

        return false;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        size = 0;
    }

    private int indexFor(E value, int length) {
        if (value == null) {
            return 0;
        }

        int hash = value.hashCode();
        hash = hash ^ (hash >>> 16); // spread hash giống idea của JDK
        return (length - 1) & hash;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Node<E>[] oldTable = table;
        int newCapacity = oldTable.length * 2;
        Node<E>[] newTable = (Node<E>[]) new Node[newCapacity];

        for (Node<E> head : oldTable) {
            Node<E> current = head;
            while (current != null) {
                Node<E> next = current.next;

                int newIndex = indexFor(current.value, newCapacity);
                current.next = newTable[newIndex];
                newTable[newIndex] = current;

                current = next;
            }
        }

        table = newTable;
        threshold = (int) (newCapacity * LOAD_FACTOR);
    }

    public void printStructure() {
        System.out.println("===== MyHashSet Structure =====");
        for (int i = 0; i < table.length; i++) {
            System.out.print("bucket[" + i + "]: ");
            Node<E> current = table[i];
            while (current != null) {
                System.out.print(current.value + " -> ");
                current = current.next;
            }
            System.out.println("null");
        }
        System.out.println("size = " + size);
        System.out.println("capacity = " + table.length);
        System.out.println("threshold = " + threshold);
    }

    private static class Node<E> {
        E value;
        Node<E> next;

        Node(E value, Node<E> next) {
            this.value = value;
            this.next = next;
        }
    }
}