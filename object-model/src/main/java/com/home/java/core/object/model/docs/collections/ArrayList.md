1. `ArrayList` là gì?
    - Là một class
    - Là một **implementation của `List`**.
    - Là một **Danh sách mảng động (Dynamic Array)** -> để chứa các phần tử.
    - Ý quan trọng nhất:
      > Bản chất của nó là **một lớp bọc quản lý một mảng có thể tự nới ra khi cần**.

---

# 2) Bản chất cốt lõi của `ArrayList`

Muốn hiểu `ArrayList`, phải nhớ 3 ý:

## a. Nó lưu phần tử trong mảng

Bên dưới có một `Object[]`.

## b. Nó có `size`

Đây là số phần tử thực sự đang có trong list.

## c. Nó có `capacity`

Đây là kích thước mảng backend hiện tại.

Ví dụ tư duy:

```java
class MyArrayList<E> {
    Object[] elementData;
    int size;
}
```

---

# 3) `size` và `capacity` khác nhau thế nào?

Đây là chỗ rất nhiều người nhầm.

## `size`

Là số phần tử logic mà `List` đang chứa.

## `capacity`

Là số ô của mảng backend hiện tại.

Ví dụ:

```java
List<String> list = new ArrayList<>();
list.

add("A");
list.

add("B");
list.

add("C");
```

Có thể hình dung bên trong:

```java
elementData =["A","B","C",null,null,null,null,null,null,null]
size =3
capacity =10
```

Tức là:

* list đang có 3 phần tử
* nhưng mảng bên dưới đang có chỗ cho 10 phần tử

---

# 4) Vì sao gọi là “dynamic array”?

Vì mảng Java bình thường có kích thước cố định:

```java
String[] arr = new String[10];
```

Sau khi tạo xong thì không thể “nới” mảng đó.

Còn `ArrayList` tạo cảm giác như có thể lớn lên vô hạn.
Thực ra nó **không kéo dãn mảng cũ**.

Nó làm theo cách này:

1. có một mảng cũ
2. khi mảng đầy, tạo một mảng mới lớn hơn
3. copy dữ liệu từ mảng cũ sang mảng mới
4. trỏ sang mảng mới

Nên đúng bản chất là:

> `ArrayList` không làm mảng “to lên”, mà **thay mảng cũ bằng mảng mới lớn hơn**.

---

# 5) Thêm phần tử vào cuối hoạt động thế nào?

Ví dụ hiện tại:

```java
[A,B,C,null,null]
size =3
```

Khi:

```java
list.add("D");
```

thì `ArrayList` chỉ cần:

* gán `"D"` vào `elementData[3]`
* tăng `size` lên 4

Sau đó:

```java
[A,B,C,D,null]
size =4
```

Thao tác này rất rẻ.

---

# 6) Khi mảng đầy thì chuyện gì xảy ra?

Ví dụ:

```java
[A,B,C]
size =3
capacity =3
```

Bây giờ gọi:

```java
list.add("D");
```

Không còn chỗ nữa, nên `ArrayList` phải:

1. tạo mảng mới lớn hơn
2. copy `A, B, C` sang mảng mới
3. thêm `D`
4. cập nhật `elementData`

Ví dụ:

```java
old:[A,B,C]
        new:[A,B,C,D,null,null]
```

Đây là lý do vì sao:

> `add(e)` thường nhanh, nhưng có những lúc đột nhiên đắt vì phải resize + copy.

---

# 7) Vì sao `add(e)` thường được coi là O(1)?

Không phải mỗi lần thêm đều resize.

Phần lớn thời gian, nó chỉ làm:

* `elementData[size] = e`
* `size++`

=> rất nhanh, gần O(1)

Chỉ thi thoảng mới gặp resize, lúc đó tốn O(n) để copy.

Vì vậy người ta nói:

> `add(e)` của `ArrayList` là **amortized O(1)**

Tức là:

* có vài lần rất đắt
* nhưng nếu nhìn trung bình trên nhiều lần add thì chi phí mỗi lần vẫn gần O(1)

---

# 8) Vì sao `get(index)` rất nhanh?

Vì backend là mảng.

Ví dụ:

```java
list.get(5);
```

Mảng cho phép truy cập trực tiếp tới phần tử ở vị trí bất kỳ mà **không cần đi từng bước từ đầu**.

Đó là lý do:

> `get(index)` là O(1)

Đây là thế mạnh lớn nhất của `ArrayList`.

---

# 9) Vì sao `set(index, e)` cũng nhanh?

Ví dụ:

```java
list.set(2,"X");
```

Bản chất chỉ là thay giá trị ở ô số 2 trong mảng.

Không cần dời phần tử khác.

Nên:

> `set(index, e)` cũng là O(1)

---

# 10) Vì sao chèn vào giữa lại chậm?

Ví dụ ban đầu:

```java
[A,B,C,D]
```

Gọi:

```java
list.add(1,"X");
```

Kết quả logic mong muốn:

```java
[A,X,B,C,D]
```

Muốn vậy thì các phần tử từ index 1 trở đi phải dịch sang phải:

* `D` sang ô mới
* `C` sang ô mới
* `B` sang ô mới

Rồi mới đặt `X` vào index 1.

Nên:

> chèn giữa = phải **shift** dữ liệu

=> chi phí thường là O(n)

---

# 11) Vì sao xóa giữa cũng chậm?

Ví dụ:

```java
[A,B,C,D]
```

Gọi:

```java
list.remove(1); // xóa B
```

Kết quả cần là:

```java
[A,C,D]
```

Để giữ cho các index liên tiếp đúng, `ArrayList` phải kéo trái:

* `C` từ 2 về 1
* `D` từ 3 về 2

Rồi giảm `size`.

Nên:

> xóa giữa cũng phải shift

=> O(n)

---

# 12) Xóa cuối có đặc biệt hơn không?

Có.

Ví dụ:

```java
[A,B,C,D]
```

Xóa phần tử cuối:

```java
list.remove(3);
```

Thì hầu như không cần shift.

Chỉ cần:

* lấy phần tử cũ
* gán slot cuối thành `null`
* giảm `size`

Nên xóa cuối rẻ hơn nhiều so với xóa giữa.

---

# 13) Tại sao phải gán `null` cho slot thừa?

Ví dụ sau khi xóa cuối:

```java
[A,B,C,D,null]
size =4
```

Xóa `D` thì nếu chỉ giảm `size` mà không gán:

```java
elementData[3]=null;
```

thì mảng vẫn giữ reference tới object cũ.

Điều đó làm object không được GC giải phóng dù về mặt logic list không còn chứa nó nữa.

Nên `ArrayList` phải clear reference thừa để tránh giữ object sống vô ích.

Đây không phải “memory leak” kiểu C/C++, nhưng là **giữ reference thừa không cần thiết**.

---

# 14) `ArrayList` lưu object thật hay lưu reference?

Nó lưu **reference**.

Ví dụ:

```java
class User {
    String name;

    User(String name) {
        this.name = name;
    }
}
```

```java
User u = new User("Alice");
list.

add(u);
```

Trong `ArrayList`, cái được lưu là reference tới `u`, không phải một bản copy của object.

Ví dụ:

```java
import java.util.ArrayList;
import java.util.List;

class User {
    String name;

    User(String name) {
        this.name = name;
    }
}

public class Main {
    public static void main(String[] args) {
        List<User> list = new ArrayList<>();

        User u = new User("Alice");
        list.add(u);

        u.name = "Bob";

        System.out.println(list.get(0).name); // Bob
    }
}
```

Tức là:

* `ArrayList` không clone object
* nó chỉ giữ tham chiếu tới object đó

---

# 15) `ArrayList` có cho phép phần tử trùng nhau không?

Có, vì nó là `List`.

Ví dụ:

```java
List<String> list = new ArrayList<>();
list.

add("A");
list.

add("A");
list.

add("B");

System.out.

println(list); // [A, A, B]
```

Điều này hoàn toàn hợp lệ.

Vì với `ArrayList`, thứ quan trọng là:

* thứ tự
* vị trí
* sequence

Chứ không phải uniqueness như `Set`.

---

# 16) `ArrayList` có cho phép `null` không?

Có.

Ví dụ:

```java
List<String> list = new ArrayList<>();
list.

add(null);
list.

add("A");

System.out.

println(list); // [null, A]
```

Điều này cũng hợp lệ.

Nên khi dùng `ArrayList`, bạn phải tự cẩn thận null-safety trong code của mình.

---

# 17) `contains()` có nhanh không?

Không.

Rất nhiều người nhầm chỗ này.

`ArrayList` nhanh ở:

* `get(index)`
* `set(index, e)`

Nhưng:

```java
list.contains(x)
```

lại là bài toán khác.

Nó phải duyệt từng phần tử rồi so sánh bằng `equals()`.

=> `contains()` là O(n)

Tức là:

> truy cập theo vị trí nhanh
> nhưng tìm theo giá trị thì không nhanh

---

# 18) `indexOf()` và `lastIndexOf()` hoạt động thế nào?

Ví dụ:

```java
List<String> list = new ArrayList<>();
list.

add("A");
list.

add("B");
list.

add("A");

System.out.

println(list.indexOf("A"));     // 0
        System.out.

println(list.lastIndexOf("A")); // 2
```

* `indexOf` duyệt từ đầu đến cuối
* `lastIndexOf` duyệt từ cuối về đầu

Cả hai đều là O(n)

---

# 19) `remove(Object o)` có bản chất gì?

Ví dụ:

```java
list.remove("B");
```

Thao tác này thường gồm 2 bước:

## Bước 1: tìm phần tử đầu tiên bằng `equals()`

=> O(n)

## Bước 2: xóa phần tử đó và shift phần còn lại

=> O(n)

Nên tổng thể vẫn là O(n)

---

# 20) `ArrayList` tăng capacity như thế nào?

OpenJDK thường tăng theo kiểu gần **1.5 lần**.

Tư duy:

```java
newCapacity =oldCapacity +oldCapacity /2
```

Ví dụ:

* 10 -> 15
* 15 -> 22
* 22 -> 33

Tại sao không tăng mỗi lần 1 ô?

Vì nếu tăng 1 ô mỗi lần thì mỗi lần đầy lại phải copy, tổng chi phí sẽ cực lớn.

Tại sao không tăng gấp đôi luôn?

Vì như vậy có thể lãng phí bộ nhớ hơn mức cần thiết.

Nên tăng khoảng 1.5x là một trade-off khá hợp lý giữa:

* số lần resize
* lượng bộ nhớ dư

---

# 21) `ArrayList` có thread-safe không?

Không.

`ArrayList` **không synchronized**.

Nếu nhiều thread cùng sửa một `ArrayList` mà không có cơ chế bảo vệ bên ngoài thì có thể xảy ra:

* race condition
* trạng thái không nhất quán
* lỗi khi iterate
* hành vi khó đoán

Điểm rất quan trọng:

> `ArrayList` nhanh một phần vì nó không phải trả chi phí đồng bộ mặc định.

---

# 22) `ArrayList` và iterator có gì đáng chú ý?

Iterator của `ArrayList` thường là **fail-fast**.

Ví dụ:

```java
for(String s :list){
        if("A".

equals(s)){
        list.

remove(s); // dễ gây ConcurrentModificationException
    }
            }
```

Vì trong lúc iterator đang duyệt, bạn lại sửa cấu trúc list từ bên ngoài iterator.

Khi phát hiện điều này, Java thường ném:

```java
ConcurrentModificationException
```

Ý nghĩa của fail-fast:

* không phải để thread-safe
* mà để phát hiện bug sớm

---

# 23) `ArrayList` khác array bình thường ở đâu?

## Giống

* có index
* truy cập ngẫu nhiên nhanh
* backend là mảng

## Khác

* có `size` riêng
* có thể grow
* có API phong phú hơn
* hoạt động theo abstraction `List`
* dùng generic tiện hơn

Nói cách khác:

> `ArrayList` không phải là mảng, mà là **một abstraction quản lý mảng cho bạn**.

---

# 24) `ArrayList` và generic

Bạn viết:

```java
ArrayList<String> list = new ArrayList<>();
```

Nhưng bên dưới, do type erasure, backend thực tế vẫn xoay quanh `Object[]`, không phải một `String[]` generic thật sự
theo nghĩa runtime.

Điều này giải thích vì sao:

* generic an toàn chủ yếu ở compile-time
* bên trong JDK vẫn có cast ở một số chỗ
* không thể tạo `new E[]` trực tiếp theo cách thông thường

---

# 25) Vì sao `ArrayList` rất mạnh trong thực tế?

Vì phần lớn bài toán đời thực có pattern như sau:

* thêm dữ liệu vào cuối
* duyệt dữ liệu
* đọc theo index
* ít chèn/xóa ở giữa

Mà đây lại chính là vùng mạnh của `ArrayList`.

Cho nên trong rất nhiều case phổ thông, `ArrayList` là lựa chọn mặc định hợp lý.

---

# 26) Khi nào `ArrayList` không phù hợp?

Khi bài toán có pattern như:

* chèn đầu liên tục
* xóa đầu liên tục
* chèn/xóa giữa quá thường xuyên
* nhiều thread cùng sửa mà không kiểm soát
* list rất lớn và hay resize/shift

Lúc đó `ArrayList` không còn đẹp nữa.

---

# 27) Pseudo-code đơn giản để thấy lõi của `ArrayList`

```java
class MyArrayList<E> {
    private Object[] data = new Object[10];
    private int size = 0;

    public void add(E e) {
        ensureCapacity();
        data[size] = e;
        size++;
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        checkIndex(index);
        return (E) data[index];
    }

    public void add(int index, E e) {
        checkIndexForAdd(index);
        ensureCapacity();

        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }

        data[index] = e;
        size++;
    }

    @SuppressWarnings("unchecked")
    public E remove(int index) {
        checkIndex(index);
        E oldValue = (E) data[index];

        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }

        data[size - 1] = null;
        size--;

        return oldValue;
    }

    private void ensureCapacity() {
        if (size == data.length) {
            Object[] newData = new Object[data.length + data.length / 2];
            for (int i = 0; i < data.length; i++) {
                newData[i] = data[i];
            }
            data = newData;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }
}
```

Code này chưa đầy đủ như JDK, nhưng đủ để thấy đúng lõi:

* backend là `Object[]`
* có `size`
* khi đầy thì grow
* chèn/xóa giữa phải shift

---

# 28) Big-O cần nhớ của `ArrayList`

## Nhóm mạnh

* `get(index)` → O(1)
* `set(index, e)` → O(1)
* `add(e)` cuối list → amortized O(1)

## Nhóm yếu

* `add(index, e)` → O(n)
* `remove(index)` → O(n)
* `remove(object)` → O(n)
* `contains(object)` → O(n)
* `indexOf(object)` → O(n)

---

# 29) Chốt bản chất trong một câu

> `ArrayList` là một `List` được cài bằng mảng động: cực mạnh khi đọc theo index và thêm cuối, nhưng chèn/xóa ở giữa
> phải trả giá bằng việc dời dữ liệu.

---

# 30) Ghi nhớ theo chuỗi nhân quả

Bạn nên nhớ theo logic này:

**Vì backend là mảng liên tục**
→ truy cập theo index nhanh
→ duyệt tuần tự tốt
→ thêm cuối thường rẻ

Nhưng cũng vì backend là mảng liên tục
→ chèn giữa phải dời phải
→ xóa giữa phải kéo trái
→ khi đầy phải tạo mảng mới và copy

Nếu nhớ được chuỗi này, bạn đã nắm đúng bản chất của `ArrayList`.

---

Nếu muốn, bước tiếp theo tôi sẽ viết tiếp **`LinkedList`** theo đúng cùng style này để bạn thấy sự đối lập rất rõ giữa:

* `ArrayList` = tư duy **mảng liên tục**
* `LinkedList` = tư duy **node liên kết**
