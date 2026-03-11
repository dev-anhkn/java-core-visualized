package com.home.java.core.object.model.collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyArrayList<E> {

    private static final Logger log = LoggerFactory.getLogger(MyArrayList.class);

    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Mảng rỗng dùng chung khi new MyArrayList()
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    /**
     * Mảng rỗng dùng chung khi người dùng truyền initialCapacity = 0
     * Tách riêng để dễ hiểu logic hơn.
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     * Nơi chứa dữ liệu thực tế
     */
    private Object[] elementData;

    /**
     * Số phần tử thực tế đang có
     */
    private int size;

    public MyArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
        log("Khởi tạo bằng constructor rỗng");
        log("elementData đang trỏ tới mảng rỗng dùng chung");
        printInternalState();
    }

    public MyArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
            log("Khởi tạo với initialCapacity = " + initialCapacity);
            log("Cấp phát ngay mảng có length = " + initialCapacity);
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
            log("Khởi tạo với initialCapacity = 0");
            log("Dùng mảng rỗng, chưa cấp phát phần tử nào");
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
        this.size = 0;
        printInternalState();
    }

    // =====================================
    // Các hàm public phổ biến
    // =====================================

    public int size() {
        log("Gọi size() -> " + size);
        return size;
    }

    public boolean isEmpty() {
        boolean result = size == 0;
        log("Gọi isEmpty() -> " + result);
        return result;
    }

    public void add(E e) {
        add(e, elementData, size);
    }

    private void add(E e, Object[] elementData, int s) {
        logSeparator();
        log("Bắt đầu add(" + e + ")");
        if (size == elementData.length) {
            elementData = grow();
        }
        log("Đặt phần tử vào elementData[" + size + "]");
        elementData[s] = e;
        size = s + 1;
        log("Tăng size lên thành " + size);
        printInternalState();
    }

    public void add(int index, E element) {
        logSeparator();
        log("Bắt đầu add(index = " + index + ", element = " + element + ")");
        checkPositionIndex(index);
        final int s = size;
        Object[] elementDataF = this.elementData;

        if (s == elementDataF.length) {
            elementDataF = grow();
        }

        log("Dịch các phần tử từ phải sang trái để chèn vào giữa");
        for (int i = s; i > index; i--) {
            log("  elementData[" + i + "] = elementData[" + (i - 1) + "] -> " + elementData[i - 1]);
            elementDataF[i] = elementDataF[i - 1];
        }
        log("Gán elementData[" + index + "] = " + element);
        elementDataF[index] = element;

        size = s + 1;
        log("Tăng size lên thành " + size);
        printInternalState();
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        logSeparator();
        log("Bắt đầu get(" + index + ")");

        checkElementIndex(index);

        E value = (E) elementData[index];
        log("Trả về phần tử tại index " + index + " = " + value);
        return value;
    }

    @SuppressWarnings("unchecked")
    public E set(int index, E e) {
        logSeparator();
        log("Bắt đầu set(index = " + index + ", element = " + e + ")");

        checkElementIndex(index);

        E oldValue = (E) elementData[index];
        log("Giá trị cũ = " + oldValue);

        elementData[index] = e;
        log("Đã thay bằng giá trị mới = " + e);

        printInternalState();
        return oldValue;
    }

    @SuppressWarnings("unchecked")
    public E remove(int index) {
        logSeparator();
        log("Bắt đầu remove(index = " + index + ")");

        checkElementIndex(index);

        E oldValue = (E) elementData[index];
        log("Phần tử bị xoá = " + oldValue);

        int numMoved = size - index - 1;
        log("Số phần tử cần dịch trái = " + numMoved);

        for (int i = index; i < size - 1; i++) {
            log("  elementData[" + i + "] = elementData[" + (i + 1) + "] -> " + elementData[i + 1]);
            elementData[i] = elementData[i + 1];
        }

        log("Gán null vào ô cuối cũ elementData[" + (size - 1) + "] để nhả reference");
        elementData[size - 1] = null;

        size--;
        log("Giảm size xuống còn " + size);

        printInternalState();
        return oldValue;
    }

    public boolean contains(E e) {
        logSeparator();
        log("Bắt đầu contains(" + e + ")");
        boolean result = indexOf(e) >= 0;
        log("Kết quả contains = " + result);
        return result;
    }

    public int indexOf(E e) {
        logSeparator();
        log("Bắt đầu indexOf(" + e + ")");

        if (e == null) {
            for (int i = 0; i < size; i++) {
                log("So sánh elementData[" + i + "] == null ?");
                if (elementData[i] == null) {
                    log("Tìm thấy null tại index = " + i);
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                log("So sánh " + e + ".equals(elementData[" + i + "]) ?");
                if (e.equals(elementData[i])) {
                    log("Tìm thấy tại index = " + i);
                    return i;
                }
            }
        }

        log("Không tìm thấy");
        return -1;
    }

    public void clear() {
        logSeparator();
        log("Bắt đầu clear()");

        for (int i = 0; i < size; i++) {
            log("  elementData[" + i + "] = null");
            elementData[i] = null;
        }

        size = 0;
        log("Đặt size = 0");

        printInternalState();
    }

    public int capacity() {
        return elementData.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(elementData[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    // =====================================
    // Logic bên trong gần kiểu JDK
    // =====================================

    private Object[] grow() {
        return grow(size + 1);
    }

    /**
     * Grow theo kiểu tăng 1.5 lần, rất gần tư duy JDK.
     */
    private Object[] grow(int minCapacity) {
        int oldCapacity = elementData.length;
        log("Bắt đầu grow(minCapacity = " + minCapacity + ")");
        log("oldCapacity = " + oldCapacity);

        if (oldCapacity > 0 || elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            log("Tính newCapacity = oldCapacity + (oldCapacity >> 1) = " + newCapacity);

            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
                log("newCapacity < minCapacity -> ép newCapacity = " + newCapacity);
            }

            elementData = Arrays.copyOf(elementData, newCapacity);
        } else {
            int newCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
            log("Đây là mảng rỗng mặc định -> cấp phát lần đầu với capacity = " + newCapacity);
            elementData = new Object[newCapacity];
        }

        log("Đã thay elementData sang mảng mới, capacity mới = " + elementData.length);
        return elementData;
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

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    public void printInternalState() {
        log.info(">>> Trạng thái nội bộ");
        log.info("    size     = {}", size);
        log.info("    capacity = {}", elementData.length);
        String data = Arrays.toString(elementData);
        log.info("    data     = {}", data);
        log.info("");
    }

    public static void printInternalState(List<String> list) {
        log.info(">>> Trạng thái nội bộ");
        log.info("    size     = {}", list.size());
        log.info("    data     = {}", list);
        log.info("");
    }

    private void log(String message) {
        log.info("[MyArrayList] {}", message);
    }

    private static void logSeparator() {
        log.info("--------------------------------------------------");
    }

    // =====================================
    // Main test
    // =====================================

    static void main() {
        //
        List<String> strings = new ArrayList<>();
        printInternalState(strings);
        logSeparator();
        log.info("Sau khi new ArrayList<>():");
        strings.add("A");
        strings.add("B");
        strings.add("C");
        strings.add(1, "X");
        log.info("List hiện tại {}", strings);
        String get2ArrayList = strings.get(2);
        log.info("get(2) {}", get2ArrayList);
        logSeparator();
        //
        MyArrayList<String> list = new MyArrayList<>();
        log.info("Sau khi new MyArrayList<>():");
        list.printInternalState();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        list.add("E");
        list.add("F");
        list.add("G");
        list.add("H");
        list.add("I");
        list.add("K");
//        list.add("L");
//        list.add("M");
//        list.add("N");
//        list.add("O");
//        list.add("P");
//        list.add("Q");
        //
        list.add(9, "X");
        log.info("List hiện tại {}", list);
        String get2Mylist = strings.get(2);
        log.info("get(2) {}", get2Mylist);

        list.set(2, "Y");
        log.info("Sau set: {}", list);
        list.remove(1);
        log.info("Sau remove index 1: {}", list);
        log.info("contains(\"C\") = {}", list.contains("C"));
        log.info("indexOf(\"C\") = {}", list.indexOf("C"));
        list.clear();
        log.info("Sau clear: {}", list);
    }
}
