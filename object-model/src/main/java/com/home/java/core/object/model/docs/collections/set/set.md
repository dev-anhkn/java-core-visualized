# Set trong Java

## 1. Set là gì?

- `Set` là một collection dùng để **lưu tập hợp các phần tử không trùng lặp**.

- Nói ngắn gọn:
    - `List` quan tâm tới **thứ tự** và **cho phép trùng**
    - `Set` quan tâm tới **tính duy nhất**
    - Một phần tử đã tồn tại rồi thì thêm lại **không vào nữa**

``` java
Set<String> set = new HashSet<>();
set.add("A");
set.add("B");
set.add("A");
System.out.println(set); // chỉ có A, B
```

## 2. Bản chất quan trọng nhất của Set

> `Set` không được sinh ra để truy cập theo index như `List`, mà để đảm bảo một phần tử chỉ tồn tại duy nhất một lần.

- Tức là khi dùng `Set`, mục tiêu chính thường là:
    - chống trùng dữ liệu
    - kiểm tra phần tử đã tồn tại chưa
    - quản lý tập giá trị unique

## 3. Set không có index

``` java
set.get(0); // sai
```

- Nó chỉ quan tâm:
    - phần tử có tồn tại hay không
    - phần tử có bị trùng hay không
- Cho nên khi dùng `Set`, ta thường
    - `add()`
    - `contains()`
    - `remove()`
    - duyệt bằng `for-each` hoặc `iterator`

## 4. Các implementation quan trọng của Set

Trong Java, 3 loại hay gặp nhất là:

### 4.1 `HashSet`

* nhanh nhất trong đa số trường hợp
* không đảm bảo thứ tự
* cho phép 1 phần tử `null`

### 4.2 `LinkedHashSet`

* vẫn gần giống `HashSet`
* **giữ thứ tự thêm vào**
* cho phép 1 phần tử `null`

### 4.3 `TreeSet`

* tự sắp xếp phần tử theo thứ tự tăng dần
* không dùng hash, dùng cây đỏ đen
* **không cho `null`** trong đa số trường hợp
* chậm hơn `HashSet` khi thêm / tìm / xóa

# 5. So sánh nhanh 3 loại Set

| Loại Set        | Có chống trùng |    Giữ thứ tự thêm vào     | Có sắp xếp |         Tốc độ         |
|-----------------|:--------------:|:--------------------------:|:----------:|:----------------------:|
| `HashSet`       |       Có       |           Không            |   Không    |         Nhanh          |
| `LinkedHashSet` |       Có       |             Có             |   Không    | Hơi chậm hơn `HashSet` |
| `TreeSet`       |       Có       | Không theo insertion order |     Có     |        Chậm hơn        |

# 6. Set chống trùng bằng cách nào?

## 6.1 Với `HashSet`

- `HashSet` dùng `hashCode()` và `equals()` để xác định trùng.
- Khi thêm một phần tử:
    - Bước 1: Java gọi `hashCode()` để tính xem phần tử nên nằm ở vùng nào.
    - Bước 2: Nếu vùng đó đã có phần tử khác, Java sẽ dùng `equals()` để so sánh.
    - Bước 3:
        - nếu `equals()` là `true` → coi là trùng → không thêm
        - nếu `equals()` là `false` → không trùng → thêm vào

## 6.2 Ý nghĩa cực kỳ quan trọng

- Khi đưa object tự tạo vào `Set`, nếu không override `equals()` và `hashCode()` đúng, thì `Set` có thể **không chống
  trùng như bạn tưởng**.

``` java
class User {
    String email;
    User(String email) {
        this.email = email;
    }
}
```

``` java
Set<User> users = new HashSet<>();
users.add(new User("a@gmail.com"));
users.add(new User("a@gmail.com"));
System.out.println(users.size()); // có thể là 2
```

- Vì sao? -> Vì 2 object này là 2 vùng nhớ khác nhau. Nếu không override `equals()` và `hashCode()`, Java coi chúng là
  khác nhau.

## 6.3 Muốn Set chống trùng đúng thì phải override

``` java
import java.util.Objects;

class User {
    String email;

    User(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
```

Lúc này:

``` java
Set<User> users = new HashSet<>();
users.add(new User("a@gmail.com"));
users.add(new User("a@gmail.com"));
System.out.println(users.size()); // 1
```

# 7. `HashSet` bên trong thực chất là gì?

> `HashSet` thực chất dùng `HashMap` ở bên dưới.

- Tức là khi bạn viết:

``` java
Set<String> set = new HashSet<>();
set.add("A");
```

- bên trong gần như nó làm kiểu:

``` java
map.put("A",PRESENT);
```

- Trong đó:
    - key = phần tử của set
    - value = một object giả, không quan trọng

> `HashSet` chỉ tận dụng phần key của `HashMap` để lưu phần tử duy nhất.

Đây là lý do vì sao:

* `HashSet` có cơ chế hash
* `HashSet` nhanh
* `HashSet` phụ thuộc vào `hashCode()` và `equals()`

---

# 8. Vì sao `HashSet` cho phép 1 phần tử `null`?

Vì `HashMap` cho phép 1 key là `null`.

Mà `HashSet` dùng `HashMap` ở dưới, nên nó cũng cho phép 1 `null`.

Ví dụ:

``` java
Set<String> set = new HashSet<>();
set.

add(null);
set.

add(null);

System.out.

println(set.size()); // 1
```

Chỉ có 1 `null`, vì `Set` vẫn phải đảm bảo không trùng.

---

# 9. Các thao tác phổ biến với Set

## 9.1 add

``` java
Set<String> set = new HashSet<>();
set.

add("A");
set.

add("B");
set.

add("A");
```

* thêm mới thành công → trả về `true`
* đã tồn tại rồi → không thêm, trả về `false`

Ví dụ:

``` java
System.out.println(set.add("C")); // true
        System.out.

println(set.add("C")); // false
```

Đây là điểm khá hay của `Set`.

---

## 9.2 contains

``` java
set.contains("A");
```

Dùng để kiểm tra phần tử có trong tập hay không.

Với `HashSet`, thao tác này thường rất nhanh.

Đây là lý do thực tế nhiều bài toán dùng `Set` thay vì `List`.

Ví dụ:

* cần check tồn tại rất nhiều lần
* cần lọc trùng
* cần membership lookup

---

## 9.3 remove

``` java
set.remove("A");
```

Xóa phần tử khỏi `Set`.

---

## 9.4 size

``` java
set.size();
```

Trả về số phần tử unique hiện có.

---

## 9.5 duyệt phần tử

``` java
for(String item :set){
        System.out.

println(item);
}
```

Hoặc:

``` java
Iterator<String> iterator = set.iterator();
while(iterator.

hasNext()){
        System.out.

println(iterator.next());
        }
```

---

# 10. Thứ tự phần tử trong Set

## 10.1 `HashSet`

Không đảm bảo thứ tự.

``` java
Set<Integer> set = new HashSet<>();
set.

add(3);
set.

add(1);
set.

add(2);

System.out.

println(set);
```

Kết quả có thể là:

``` java
[1,2,3]
```

hoặc

``` java
[3,1,2]
```

hoặc thứ tự khác.

Không được dựa vào thứ tự của `HashSet`.

---

## 10.2 `LinkedHashSet`

Giữ đúng thứ tự thêm vào.

``` java
Set<Integer> set = new LinkedHashSet<>();
set.

add(3);
set.

add(1);
set.

add(2);

System.out.

println(set); // [3, 1, 2]
```

---

## 10.3 `TreeSet`

Tự sắp xếp.

``` java
Set<Integer> set = new TreeSet<>();
set.

add(3);
set.

add(1);
set.

add(2);

System.out.

println(set); // [1, 2, 3]
```

---

# 11. Độ phức tạp thời gian

## 11.1 `HashSet`

Trung bình:

* `add()` → `O(1)`
* `contains()` → `O(1)`
* `remove()` → `O(1)`

Nhưng nếu hash quá tệ hoặc collision nhiều, có thể chậm hơn.

---

## 11.2 `LinkedHashSet`

Gần giống `HashSet`, nhưng thêm chi phí để giữ liên kết thứ tự.

---

## 11.3 `TreeSet`

* `add()` → `O(log n)`
* `contains()` → `O(log n)`
* `remove()` → `O(log n)`

Vì dùng cây, không phải hash.

---

# 12. Khi nào dùng Set thay vì List?

Dùng `Set` khi:

* cần loại bỏ trùng lặp
* không cần truy cập theo index
* cần check tồn tại nhanh
* dữ liệu mang ý nghĩa “tập hợp duy nhất”

Ví dụ:

### Dùng `List`

* danh sách lịch sử giao dịch
* danh sách comment
* danh sách sản phẩm theo thứ tự hiển thị

### Dùng `Set`

* danh sách quyền user
* danh sách tag không trùng
* danh sách ID đã xử lý
* danh sách email unique

---

# 13. Ví dụ thực tế: loại bỏ trùng dữ liệu

``` java
List<String> names = Arrays.asList("An", "Bình", "An", "Cường", "Bình");

Set<String> uniqueNames = new HashSet<>(names);

System.out.

println(uniqueNames);
```

Kết quả chỉ còn các phần tử không trùng.

Nếu muốn vừa bỏ trùng vừa giữ thứ tự ban đầu:

``` java
Set<String> uniqueNames = new LinkedHashSet<>(names);
```

---

# 14. Ví dụ thực tế: kiểm tra phần tử tồn tại nhanh

## Dùng List

``` java
List<String> emails = new ArrayList<>();
emails.

add("a@gmail.com");
emails.

add("b@gmail.com");

if(emails.

contains("b@gmail.com")){
        System.out.

println("Tồn tại");
}
```

`List.contains()` phải đi dò từng phần tử.

---

## Dùng Set

``` java
Set<String> emails = new HashSet<>();
emails.

add("a@gmail.com");
emails.

add("b@gmail.com");

if(emails.

contains("b@gmail.com")){
        System.out.

println("Tồn tại");
}
```

Với dữ liệu lớn, `Set` hiệu quả hơn nhiều.

---

# 15. `TreeSet` dùng khi nào?

Dùng khi bạn cần:

* dữ liệu unique
* đồng thời cần dữ liệu luôn có thứ tự sắp xếp

Ví dụ:

``` java
Set<String> scores = new TreeSet<>();
scores.

add("C");
scores.

add("A");
scores.

add("B");

System.out.

println(scores); // [A, B, C]
```

---

## 15.1 Lưu ý với `TreeSet`

`TreeSet` cần biết cách so sánh phần tử.

Nó dùng:

* `Comparable`
* hoặc `Comparator`

Ví dụ:

``` java
TreeSet<Integer> set = new TreeSet<>();
set.

add(3);
set.

add(1);
set.

add(2);
```

Được vì `Integer` đã có `Comparable`.

Nhưng object tự tạo thì phải tự định nghĩa cách so sánh.

---

# 16. Lỗi hay gặp với Set

## 16.1 Nghĩ rằng `Set` có index

Sai.

``` java
set.get(0); // không có
```

---

## 16.2 Nghĩ rằng `HashSet` giữ thứ tự

Sai.

Muốn giữ thứ tự thêm vào thì dùng `LinkedHashSet`.

---

## 16.3 Quên override `equals()` và `hashCode()`

Đây là lỗi cực phổ biến.

Khi lưu object tự tạo trong `HashSet`, nếu không override đúng, chống trùng sẽ sai logic nghiệp vụ.

---

## 16.4 Dùng object mutable làm key logic

Ví dụ object đã add vào `HashSet`, sau đó đổi field ảnh hưởng tới `hashCode()` / `equals()`.

Điều này rất nguy hiểm.

Ví dụ:

``` java
class User {
    String email;
}
```

Nếu `email` tham gia vào `hashCode()` mà sau khi add vào set lại đổi `email`, thì object có thể bị “lạc vị trí” logic
trong hash structure.

=> Tốt nhất:

* field dùng cho `equals()` / `hashCode()` nên ổn định
* hoặc object nên immutable

---

# 17. So sánh Set và List thật dễ hiểu

## `List`

Giống như một hàng người:

* ai đứng trước, ai đứng sau là quan trọng
* có thể có nhiều người trùng tên
* lấy theo vị trí được

## `Set`

Giống như một danh sách check tên:

* tên chỉ được xuất hiện một lần
* không quan tâm đứng vị trí số mấy
* chủ yếu để biết “có hay không”

---

# 18. Một số interface / class liên quan

``` java
Set<E>
HashSet<E>
LinkedHashSet<E>
SortedSet<E>
NavigableSet<E>
TreeSet<E>
```

Quan hệ cơ bản:

* `Set` là interface gốc
* `HashSet`, `LinkedHashSet`, `TreeSet` là các implementation
* `SortedSet` là Set có sắp xếp
* `NavigableSet` mạnh hơn `SortedSet`, hỗ trợ điều hướng gần nhất, lớn hơn, nhỏ hơn...

---

# 19. Ví dụ đầy đủ

## 19.1 HashSet

``` java
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();

        set.add("Java");
        set.add("Spring");
        set.add("Java");
        set.add(null);

        System.out.println("Set = " + set);
        System.out.println("Contains Java? " + set.contains("Java"));
        System.out.println("Size = " + set.size());

        set.remove("Spring");
        System.out.println("After remove = " + set);
    }
}
```

---

## 19.2 LinkedHashSet

``` java
import java.util.LinkedHashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Set<String> set = new LinkedHashSet<>();

        set.add("B");
        set.add("A");
        set.add("C");
        set.add("A");

        System.out.println(set); // [B, A, C]
    }
}
```

---

## 19.3 TreeSet

``` java
import java.util.Set;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        Set<Integer> set = new TreeSet<>();

        set.add(5);
        set.add(1);
        set.add(3);
        set.add(1);

        System.out.println(set); // [1, 3, 5]
    }
}
```

---

# 20. Kết luận cốt lõi

## Hãy nhớ 5 ý sau:

### 1. `Set` là tập hợp không trùng lặp

Đây là bản chất số 1.

### 2. `Set` không có index

Không dùng để truy cập theo vị trí.

### 3. `HashSet` dùng `hashCode()` và `equals()`

Muốn chống trùng đúng với object tự tạo thì phải override đúng.

### 4. Chọn loại Set theo nhu cầu

* nhanh, không quan tâm thứ tự → `HashSet`
* giữ thứ tự thêm vào → `LinkedHashSet`
* cần sắp xếp → `TreeSet`

### 5. `Set` rất phù hợp cho bài toán check tồn tại và loại bỏ trùng

Đây là use case mạnh nhất.

---

# 21. Ghi nhớ cực ngắn

* `List`: có thứ tự, có index, cho phép trùng
* `Set`: không trùng, không index
* `HashSet`: nhanh nhất, không đảm bảo thứ tự
* `LinkedHashSet`: giữ thứ tự thêm vào
* `TreeSet`: tự sắp xếp
* object custom trong `HashSet` phải override `equals()` và `hashCode()`

---

# 22. Câu hỏi tự kiểm tra

## Câu 1

Vì sao `Set` không có `get(index)`?

## Câu 2

Vì sao `HashSet` chống trùng được?

## Câu 3

Khi nào nên dùng `LinkedHashSet` thay vì `HashSet`?

## Câu 4

Vì sao object custom đưa vào `HashSet` mà không override `equals()` / `hashCode()` có thể bị trùng?

## Câu 5

Khác nhau cốt lõi giữa `HashSet` và `TreeSet` là gì?

---

# 23. Một câu chốt dễ nhớ

> `List` quản lý dữ liệu như một danh sách có vị trí.
> `Set` quản lý dữ liệu như một tập hợp duy nhất, không cho phép trùng.

```

Nếu muốn, tôi có thể viết tiếp cho bạn phần **Set chuyên sâu hơn theo kiểu “bản chất bên trong JDK + collision + equals/hashCode + TreeSet/Comparable/Comparator”** giống style bạn đang học với `List`.
```
