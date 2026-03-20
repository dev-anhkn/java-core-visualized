package com.home.java.core.object.model.collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public class MyLinkedList<E> {

    private static final Logger log = LoggerFactory.getLogger(MyLinkedList.class);

    private Node<E> first;
    private Node<E> last;
    private int size;

    /**
     * Node của doubly linked list
     */
    private static class Node<E> {
        private E item;
        private Node<E> prev;
        private Node<E> next;

        Node(Node<E> prev, E item, Node<E> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    public int size() {
        return size;
    }

    public void addFirst(E element) {
        log("addFirst(" + element + ")");
        linkFirst(element);
    }

    public void addLast(E element) {
        log("addLast(" + element + ")");
        linkLast(element);
    }

    public boolean add(E element) {
        addLast(element);
        return true;
    }

    public void add(int index, E element) {
        checkPositionIndex(index);
        log("add(index=" + index + ", element=" + element + ")");

        if (index == size) {
            linkLast(element);
        } else if (index == 0) {
            linkFirst(element);
        } else {
            Node<E> target = node(index);
            linkBefore(element, target);
        }
    }

    public E get(int index) {
        checkElementIndex(index);
        Node<E> x = node(index);
        log("get(" + index + ") -> " + x.item);
        return x.item;
    }

    public E removeFirst() {
        if (first == null) {
            throw new IllegalStateException("List is empty");
        }
        log("removeFirst()");
        return unlink(first);
    }

    public E removeLast() {
        if (last == null) {
            throw new IllegalStateException("List is empty");
        }
        log("removeLast()");
        return unlink(last);
    }

    public E remove(int index) {
        checkElementIndex(index);
        log("remove(index=" + index + ")");
        return unlink(node(index));
    }

    /**
     * Thêm node vào đầu list
     */
    private void linkFirst(E element) {
        Node<E> oldFirst = first;
        Node<E> newNode = new Node<>(null, element, oldFirst);

        first = newNode;

        if (oldFirst == null) {
            last = newNode;
        } else {
            oldFirst.prev = newNode;
        }

        size++;
        log("  Đã nối node mới vào đầu");
    }

    /**
     * Thêm node vào cuối list
     */
    private void linkLast(E element) {
        Node<E> oldLast = last;
        Node<E> newNode = new Node<>(oldLast, element, null);

        last = newNode;

        if (oldLast == null) {
            first = newNode;
        } else {
            oldLast.next = newNode;
        }

        size++;
        log("  Đã nối node mới vào cuối");
    }

    /**
     * Chèn node mới vào trước node succ
     */
    private void linkBefore(E element, Node<E> succ) {
        Node<E> pred = succ.prev;
        Node<E> newNode = new Node<>(pred, element, succ);

        succ.prev = newNode;

        if (pred == null) {
            first = newNode;
        } else {
            pred.next = newNode;
        }

        size++;
        log("  Đã chèn node mới trước node có giá trị = " + succ.item);
    }

    /**
     * Gỡ node x ra khỏi list
     */
    private E unlink(Node<E> x) {
        E element = x.item;
        Node<E> prev = x.prev;
        Node<E> next = x.next;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;

        log("  Đã gỡ node khỏi list");
        return element;
    }

    /**
     * Tìm node tại index
     * Nếu index nằm nửa đầu -> đi từ first
     * Nếu index nằm nửa sau -> đi từ last
     */
    private Node<E> node(int index) {
        if (index < (size >> 1)) {
            log("  node(" + index + "): duyệt từ đầu");
            Node<E> x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            log("  node(" + index + "): duyệt từ cuối");
            Node<E> x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    public void printState() {
        System.out.println("\n===== TRẠNG THÁI LINKED LIST =====");
        System.out.println("size = " + size);
        System.out.println("first = " + (first == null ? "null" : first.item));
        System.out.println("last  = " + (last == null ? "null" : last.item));

        Node<E> current = first;
        System.out.print("forward : ");
        while (current != null) {
            System.out.print(current.item);
            if (current.next != null) {
                System.out.print(" <-> ");
            }
            current = current.next;
        }
        System.out.println();

        current = last;
        System.out.print("backward: ");
        while (current != null) {
            System.out.print(current.item);
            if (current.prev != null) {
                System.out.print(" <-> ");
            }
            current = current.prev;
        }
        System.out.println();
        System.out.println("=================================\n");
    }

    private void log(String message) {
        log.info("[MyLinkedList] {}", message);
    }

    private void test() {
        List<String> linkedList = new LinkedList<>();
        linkedList.add("a");
        linkedList.add("b");

    }
}
