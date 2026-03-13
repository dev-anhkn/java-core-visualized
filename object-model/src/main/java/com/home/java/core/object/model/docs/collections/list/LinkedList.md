# LinkedList trong Java – bản chất cốt lõi

## 1. LinkedList là gì?

- `LinkedList` là một cấu trúc dữ liệu lưu phần tử theo kiểu **chuỗi node nối với nhau**.
- Khác với `ArrayList`:
    - `ArrayList` lưu dữ liệu trong **một mảng liên tiếp trong bộ nhớ**.
    - `LinkedList` lưu dữ liệu trong **các node rời rạc**, mỗi node biết:
        - giá trị của chính nó
        - node đứng trước
        - node đứng sau
- Trong Java, `LinkedList` của JDK là **doubly linked list** (*danh sách liên kết đôi*).

## 2. Bản chất cốt lõi nhất

> Mỗi phần tử không nằm cạnh nhau như mảng.
> Mỗi phần tử nằm trong một “hộp” riêng gọi là **node**.
> Các node nối nhau bằng tham chiếu.

- Danh sách: `A -> B -> C`
    - Thực chất trong bộ nhớ sẽ gần giống:
        ``` text
        first
          ↓
        [prev=null, item=A, next=NodeB] <-> [prev=NodeA, item=B, next=NodeC] <-> [prev=NodeB, item=C, next=null]
                                                                                                              ↑
                                                                                                             last
        ```
    - Tức là:
        - `first` trỏ đến node đầu
        - `last` trỏ đến node cuối
        - mỗi node có: [`prev`, `item`, `next`]

## 3. Cấu trúc bên trong của LinkedList trong JDK

- Ý tưởng bên trong gần như thế này:
    ``` java
    public class MyLinkedList<E> {
        private int size;
        private Node<E> first;
        private Node<E> last;
    
        private static class Node<E> {
            E item;
            Node<E> next;
            Node<E> prev;
    
            Node(Node<E> prev, E element, Node<E> next) {
                this.item = element;
                this.next = next;
                this.prev = prev;
            }
        }
    }
    ```
- Ý nghĩa từng phần
    - `size`: Số lượng phần tử hiện có.
    - `first`: Giữ node đầu danh sách.
    - `last`: Giữ node cuối danh sách.
    - `Node`: Là “container” bọc phần tử lại.
- Node không chỉ lưu dữ liệu `item`, mà còn lưu đường đi:
    - đi tới node sau: `next`
    - quay về node trước: `prev`
- Vì là **liên kết đôi**, nên thêm/xóa ở hai đầu rất thuận tiện.

---

## 4. Tại sao LinkedList không cần mảng?

Vì nó không cần vùng nhớ liên tiếp.

`ArrayList` phải giữ một mảng như:

```text
[10][20][30][40]
```

Nếu hết chỗ, phải tạo mảng mới lớn hơn rồi copy.

Nhưng `LinkedList` không cần vậy.
Nó chỉ cần tạo thêm một node mới rồi móc vào chuỗi.

Ví dụ đang có:

```text
A <-> B <-> C
```

Muốn thêm `D` cuối danh sách:

```text
A <-> B <-> C <-> D
```

Không cần copy cả danh sách như `ArrayList`.

---

## 5. Cơ chế thêm phần tử

## 5.1. Thêm vào cuối – `add(E e)`

Đây là thao tác rất tự nhiên với `LinkedList`.

### Trước khi thêm

```text
first                         last
  ↓                             ↓
[A] <-> [B] <-> [C]
```

### Thêm `D`

Ta tạo node mới:

```java
Node<E> newNode = new Node<>(last, e, null);
```

Nghĩa là:

- `prev = last` (node cũ cuối cùng)
- `item = e`
- `next = null` vì nó là node cuối mới

Sau đó:

- `last.next = newNode`
- `last = newNode`

### Kết quả

```text
[A] <-> [B] <-> [C] <-> [D]
```

### Nếu danh sách đang rỗng

Khi đó:

- `first = newNode`
- `last = newNode`

Tức node đầu cũng là node cuối.

---

## 5.2. Thêm vào đầu – `addFirst(E e)`

Tương tự:

- tạo node mới với `prev = null`
- `next = first`
- nếu danh sách không rỗng thì `first.prev = newNode`
- cập nhật `first = newNode`

Nếu danh sách rỗng thì `last` cũng trỏ vào node mới.

---

## 5.3. Thêm vào giữa – `add(int index, E element)`

Đây là chỗ rất nhiều người hiểu nhầm.

### Hiểu nhầm phổ biến

> LinkedList thêm ở giữa luôn nhanh.

Không hẳn.

Đúng hơn phải nói:

> **Khi đã tìm được đúng node vị trí cần chèn**, thao tác nối node là nhanh.

Nhưng để **tìm đến đúng vị trí**, `LinkedList` phải đi từng node một.

Ví dụ muốn chèn vào index = 5000:

- không thể nhảy thẳng như mảng
- phải lần lượt đi qua node 0, 1, 2, 3...

### Sau khi tìm được node đích

Giả sử đang có:

```text
A <-> B <-> D
```

Muốn chèn `C` vào giữa `B` và `D`.

Ta tạo node `C` rồi sửa liên kết:

```text
A <-> B <-> C <-> D
```

Bản chất chỉ là đổi vài con trỏ:

- `B.next = C`
- `C.prev = B`
- `C.next = D`
- `D.prev = C`

Phần “nối dây” này là rẻ.
Nhưng phần “đi bộ đến B/D” mới là tốn thời gian.

---

## 6. Cơ chế xóa phần tử

## 6.1. Xóa đầu

Nếu đang có:

```text
A <-> B <-> C
```

Xóa `A`:

- `first = A.next` tức `B`
- `B.prev = null`

Kết quả:

```text
B <-> C
```

---

## 6.2. Xóa cuối

Nếu đang có:

```text
A <-> B <-> C
```

Xóa `C`:

- `last = C.prev` tức `B`
- `B.next = null`

---

## 6.3. Xóa giữa

Ví dụ:

```text
A <-> B <-> C <-> D
```

Xóa `C`:

- `B.next = D`
- `D.prev = B`

`C` bị tách khỏi chuỗi và sẽ được GC dọn sau nếu không còn reference.

### Điểm quan trọng

Xóa giữa cũng giống thêm giữa:

- **nối lại liên kết thì nhanh**
- nhưng **đi tìm node cần xóa thì chậm**

---

## 7. Cơ chế truy cập phần tử `get(index)`

Đây là điểm yếu chí mạng của `LinkedList`.

### Với `ArrayList`

``` java
list.get(5000)
```

có thể nhảy ngay đến ô nhớ tương ứng vì mảng hỗ trợ truy cập theo chỉ số.

### Với `LinkedList`

Không có vùng nhớ liên tiếp, nên muốn lấy phần tử thứ 5000 phải lần theo chuỗi node.

JDK tối ưu một chút:

- nếu `index < size / 2` thì đi từ `first`
- ngược lại đi từ `last`

Ví dụ danh sách 10 phần tử:

- `get(1)` đi từ đầu
- `get(8)` đi từ cuối

Nhưng dù tối ưu vậy, nó vẫn không phải truy cập trực tiếp kiểu mảng.

---

## 8. Độ phức tạp – nhưng phải hiểu đúng bản chất

| Thao tác      |                   ArrayList | LinkedList | Bản chất thực sự                    |
|---------------|----------------------------:|-----------:|-------------------------------------|
| `get(index)`  |                        O(1) |       O(n) | LinkedList phải đi từng node        |
| thêm cuối     | thường O(1), đôi khi resize |       O(1) | LinkedList nối node cuối rất nhanh  |
| thêm đầu      |                        O(n) |       O(1) | LinkedList chỉ đổi `first`          |
| xóa đầu       |                        O(n) |       O(1) | LinkedList chỉ đổi `first`          |
| thêm/xóa giữa |                        O(n) |       O(n) | LinkedList mất thời gian tìm vị trí |

### Chỗ dễ hiểu sai nhất

Người ta hay nói:

- `ArrayList` thêm/xóa giữa: O(n)
- `LinkedList` thêm/xóa giữa: O(1)

Câu này **chỉ đúng nếu bạn đã có sẵn reference tới node cần thao tác**.

Nhưng trong code application bình thường, bạn thường thao tác qua `index`, nên vẫn phải tìm node trước.
Khi đó tổng thể vẫn là **O(n)**.

---

## 9. Vì sao LinkedList thường chậm hơn tưởng tượng?

Rất nhiều người mới học nghĩ:

> Không cần copy mảng, vậy LinkedList chắc sẽ nhanh hơn nhiều.

Thực tế thường ngược lại trong đa số case ứng dụng.

### Lý do 1: Truy cập ngẫu nhiên rất tệ

`get(i)` phải đi tuần tự.

### Lý do 2: CPU cache locality kém

`ArrayList` có dữ liệu nằm liền nhau trong bộ nhớ nên CPU cache rất thích.

`LinkedList` có các node nằm rải rác khắp nơi trong heap, mỗi lần duyệt phải nhảy reference liên tục.
Điều này làm truy cập bộ nhớ kém hiệu quả hơn.

### Lý do 3: Tốn thêm bộ nhớ cho mỗi node

Mỗi node không chỉ chứa `item`, mà còn thêm:

- `prev`
- `next`
- overhead của object

Nên `LinkedList` tốn bộ nhớ hơn `ArrayList` đáng kể.

---

## 10. Java LinkedList không chỉ là List, mà còn là Deque

`LinkedList` trong JDK implement cả:

- `List`
- `Deque`
- `Queue`

Nghĩa là nó có thể dùng như:

- danh sách
- hàng đợi (*queue*)
- ngăn xếp (*stack*)
- deque (*double-ended queue*: thêm/xóa hai đầu)

Ví dụ:

``` java
LinkedList<String> list = new LinkedList<>();
list.

addFirst("B");
list.

addLast("C");
list.

addFirst("A");

System.out.

println(list); // [A, B, C]
```

Đây là lý do `LinkedList` hợp hơn trong các bài toán thao tác ở **đầu và cuối**.

---

## 11. Khi nào LinkedList thực sự phù hợp?

`LinkedList` phù hợp hơn khi:

### 1. Thêm/xóa thường xuyên ở đầu danh sách

Ví dụ:

- queue
- deque
- sliding window kiểu đơn giản

### 2. Bạn thao tác nhiều bằng iterator hoặc node lân cận

Tức là đang đứng tại một vị trí rồi di chuyển tiếp sang trái/phải.

### 3. Không cần random access nhiều

Nếu code của bạn gọi `get(i)` liên tục trong vòng lặp, `LinkedList` thường là lựa chọn tệ.

---

## 12. Khi nào không nên dùng LinkedList?

Không nên dùng nếu:

### 1. Cần truy cập theo index thường xuyên

Ví dụ:

``` java
for(int i = 0; i <list.

size();

i++){
        System.out.

println(list.get(i));
        }
```

Đây là kiểu rất không hợp với `LinkedList`.

### 2. Danh sách lớn và cần hiệu năng thực tế tốt

`ArrayList` thường thắng trong đa số tình huống business thông thường.

### 3. Bạn chỉ nghĩ rằng “thêm/xóa nhiều thì chắc chắn LinkedList tốt hơn”

Điều này rất dễ sai.
Phải xem thêm/xóa ở đâu, truy cập kiểu gì, pattern sử dụng ra sao.

---

## 13. So sánh bản chất ArrayList vs LinkedList

## ArrayList

Bản chất là:

> Một mảng động (*dynamic array*).

Tư duy của nó là:

- giữ dữ liệu liền nhau
- truy cập cực nhanh theo index
- khi chèn/xóa giữa phải dịch phần tử
- đôi lúc phải resize

## LinkedList

Bản chất là:

> Một chuỗi node nối nhau bằng reference.

Tư duy của nó là:

- không cần vùng nhớ liên tiếp
- thêm/xóa ở đầu/cuối rất thuận tiện
- truy cập theo index rất chậm
- tốn bộ nhớ hơn

---

## 14. Hình dung cực ngắn để nhớ lâu

### ArrayList giống gì?

Giống dãy ghế đánh số liên tiếp:

- muốn ngồi ghế số 50: tới ngay ghế 50
- chèn người vào giữa: phải xô các người phía sau dịch chỗ

### LinkedList giống gì?

Giống đoàn tàu toa nối nhau:

- muốn tới toa số 50: phải đi qua từng toa
- tháo/gắn toa ở chỗ đang đứng: khá dễ
- nhưng tìm tới đúng toa thì mất thời gian

---

## 15. Một `MyLinkedList` tối giản để thấy bản chất

```java
public class MyLinkedList<E> {

    private int size;
    private Node<E> first;
    private Node<E> last;

    private static class Node<E> {
        E item;
        Node<E> prev;
        Node<E> next;

        Node(Node<E> prev, E item, Node<E> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    public void addLast(E e) {
        Node<E> l = last;
        Node<E> newNode = new Node<>(l, e, null);
        last = newNode;

        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    public void addFirst(E e) {
        Node<E> f = first;
        Node<E> newNode = new Node<>(null, e, f);
        first = newNode;

        if (f == null) {
            last = newNode;
        } else {
            f.prev = newNode;
        }
        size++;
    }

    public E get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    public E removeFirst() {
        if (first == null) {
            throw new IllegalStateException("List is empty");
        }

        Node<E> f = first;
        E element = f.item;
        Node<E> next = f.next;

        f.item = null;
        f.next = null;
        first = next;

        if (next == null) {
            last = null;
        } else {
            next.prev = null;
        }

        size--;
        return element;
    }

    private Node<E> node(int index) {
        if (index < (size >> 1)) {
            Node<E> x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index=" + index + ", size=" + size);
        }
    }
}
```

---

## 16. Những điều phải nhớ thật chắc

### 1. LinkedList không lưu trực tiếp thành dãy liên tiếp như mảng

Nó lưu bằng **node + liên kết**.

### 2. Java `LinkedList` là doubly linked list

Mỗi node có cả `prev` và `next`.

### 3. Mạnh ở thêm/xóa đầu cuối

Đặc biệt khi dùng như `Deque`.

### 4. Yếu ở truy cập theo index

Vì phải đi từng node.

### 5. “Thêm/xóa giữa nhanh” là câu nói chưa đủ

Phải cộng cả chi phí tìm vị trí.

### 6. Trong code thực tế, `ArrayList` thường được dùng nhiều hơn

Vì hiệu năng tổng thể thường tốt hơn cho đa số tác vụ nghiệp vụ.

---

## 17. Chốt lại bằng 1 câu

### ArrayList

> Dữ liệu nằm trong mảng động, mạnh ở truy cập nhanh theo index.

### LinkedList

> Dữ liệu nằm trong các node liên kết với nhau, mạnh ở thao tác đầu/cuối nhưng yếu ở truy cập ngẫu nhiên.

---

## 18. Câu hỏi tự kiểm tra

1. Vì sao `LinkedList` không cần resize như `ArrayList`?
2. Vì sao `get(index)` của `LinkedList` chậm?
3. Câu “LinkedList thêm/xóa giữa là O(1)” đúng trong điều kiện nào?
4. Vì sao nhiều trường hợp `ArrayList` vẫn nhanh hơn `LinkedList` dù phải dịch phần tử?
5. Khi nào nên dùng `LinkedList` như một `Deque` thay vì như một `List`?

