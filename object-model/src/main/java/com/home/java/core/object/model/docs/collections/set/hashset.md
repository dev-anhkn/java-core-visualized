# HashSet trong Java – từ định nghĩa đến bản chất bên trong

## 1. HashSet là gì?

- `HashSet` là một implementation của interface `Set` trong Java.
- Mục tiêu chính:
    - lưu các phần tử **không trùng lặp**
    - hỗ trợ thao tác **thêm**, **xóa**, **kiểm tra tồn tại** rất nhanh
    - **không đảm bảo thứ tự** phần tử khi duyệt

``` java
Set<String> set = new HashSet<>();
set.add("Java");
set.add("Spring");
set.add("Java");
System.out.println(set); // chỉ chứa Java, Spring
```

## 2. Định nghĩa ngắn gọn dễ nhớ

> `HashSet` là tập hợp không trùng lặp, hoạt động dựa trên cơ chế hash.

> `HashSet` sinh ra để quản lý dữ liệu theo kiểu “có hay không”, chứ không phải “đứng ở vị trí số mấy”.

## 3. HashSet dùng khi nào?

- Dùng `HashSet` khi cần:
    - loại bỏ phần tử trùng
    - kiểm tra một phần tử đã tồn tại hay chưa
    - lưu tập dữ liệu unique
    - tăng tốc thao tác `contains()` so với `List`

## 4. Đặc điểm quan trọng của HashSet

### 4.1 Không cho phép phần tử trùng lặp

``` java
Set<Integer> numbers = new HashSet<>();
numbers.add(10);
numbers.add(10);
System.out.println(numbers.size()); // 1
```

### 4.2 Không có index

### 4.3 Không đảm bảo thứ tự

### 4.4 Cho phép 1 phần tử `null`


## 5. Cốt lõi tư duy khi học HashSet

Điểm quan trọng nhất cần nhớ là:

> `HashSet` không tự xây dựng một cơ chế lưu trữ hoàn toàn riêng, mà bản chất nó dùng `HashMap` ở bên dưới.

Đây là câu chốt quan trọng nhất của toàn bộ phần này.

---

# 6. Bản chất bên trong: HashSet thực ra là gì?

## 6.1 Câu trả lời ngắn nhất

> `HashSet` là một lớp bọc mỏng quanh `HashMap`.

Nói thẳng:

- `HashSet` dùng `HashMap` làm bộ máy lưu trữ
- mỗi phần tử của `HashSet` được lưu thành **key** trong `HashMap`
- còn value chỉ là một object giả dùng chung

---

## 6.2 Tư duy cực ngắn

Nếu viết ý tưởng của `HashSet` theo cách đơn giản, nó gần như là:

``` java
public class HashSet<E> {
    private HashMap<E, Object> map = new HashMap<>();
    private static final Object PRESENT = new Object();

    public boolean add(E e) {
        return map.put(e, PRESENT) == null;
    }
}
```

Tức là khi bạn viết:

``` java
HashSet<String> set = new HashSet<>();
set.add("Java");
```

thì bên trong gần như là:

``` java
map.put("Java", PRESENT);
```

Ở đây:

- `"Java"` là key
- `PRESENT` là value giả

---

## 6.3 Một câu chốt rất quan trọng

> `HashSet = HashMap chỉ dùng phần key`.

---

# 7. Vì sao HashSet lại dùng HashMap?

Vì `Set` cần đúng 2 thứ:

- phần tử là duy nhất
- kiểm tra tồn tại nhanh

Mà `HashMap` đã có sẵn cơ chế đó ở phần `key`:

- key là duy nhất
- `put(key, value)` nếu key đã tồn tại thì không sinh key mới
- `containsKey(key)` rất nhanh

Cho nên JDK tận dụng luôn `HashMap` để xây `HashSet`, thay vì viết lại toàn bộ bộ máy hash table một lần nữa.

---

# 8. Cấu trúc bên dưới sâu hơn: HashMap hoạt động ra sao?

Vì `HashSet` dựa trên `HashMap`, nên muốn hiểu `HashSet`, bạn phải hiểu sơ bộ `HashMap`.

Bên trong `HashMap` có:

- một mảng bucket
- mỗi bucket là nơi chứa các node
- mỗi node chứa:
    - `hash`
    - `key`
    - `value`
    - `next`

Ý tưởng node:

``` java
static class Node<K, V> {
    final int hash;
    final K key;
    V value;
    Node<K, V> next;
}
```

Với `HashSet` thì:

- `key` = phần tử set
- `value` = `PRESENT`

Ví dụ nếu thêm `"Java"` vào `HashSet`, bên trong node gần như là:

``` java
hash = hash("Java")
key = "Java"
value = PRESENT
next = ...
```

---

# 9. Luồng thêm một phần tử vào HashSet

Giả sử có câu lệnh:

``` java
set.add("Java");
```

Bên trong thực chất là:

``` java
map.put("Java", PRESENT);
```

Sau đó `HashMap` xử lý như sau:

## Bước 1: gọi `hashCode()`

Java lấy mã băm của phần tử:

``` java
"Java".hashCode()
```

## Bước 2: tính lại hash để phân bố đều hơn

JDK thường xử lý thêm để tránh phân bố lệch quá nhiều.

Ví dụ ý tưởng:

``` java
hash = h ^ (h >>> 16)
```

## Bước 3: xác định bucket index

Sau khi có hash, Java tính phần tử sẽ rơi vào bucket nào.

Ví dụ tư duy:

``` java
index = (n - 1) & hash
```

Trong đó `n` là độ dài mảng bucket.

## Bước 4: kiểm tra bucket đó có phần tử chưa

- nếu chưa có gì -> thêm luôn
- nếu đã có -> kiểm tra collision và kiểm tra trùng

## Bước 5: kiểm tra trùng bằng `equals()`

Nếu trong bucket đã có key có cùng hash và `equals()` trả `true` thì Java coi là cùng phần tử.

=> `HashSet` không thêm nữa.

---

# 10. HashSet chống trùng bằng cách nào?

Câu trả lời đúng bản chất là:

> `HashSet` chống trùng nhờ cơ chế key unique của `HashMap`.

Chi tiết hơn:

- dùng `hashCode()` để xác định bucket
- dùng `equals()` để xác nhận có đúng là cùng phần tử hay không

### Luồng logic

Khi thêm một phần tử mới:

1. tính `hashCode()`
2. xác định bucket
3. duyệt các node trong bucket đó
4. nếu tìm thấy phần tử mà `equals()` là `true` -> coi là trùng
5. không tìm thấy -> thêm mới

---

# 11. `hashCode()` và `equals()` quan trọng thế nào?

Cực kỳ quan trọng.

Đây là 2 nền tảng quyết định `HashSet` có chống trùng đúng hay không.

## 11.1 Với object tự tạo, nếu không override đúng thì rất dễ sai

Ví dụ:

``` java
class User {
    String email;

    User(String email) {
        this.email = email;
    }
}
```

``` java
HashSet<User> users = new HashSet<>();
users.add(new User("a@gmail.com"));
users.add(new User("a@gmail.com"));

System.out.println(users.size()); // có thể là 2
```

Vì sao?

Vì 2 object này là 2 object khác nhau trong bộ nhớ.

Nếu không override `equals()` và `hashCode()`, Java mặc định so sánh theo identity, không phải theo ý nghĩa nghiệp vụ.

---

## 11.2 Override đúng thì HashSet mới chống trùng theo nghiệp vụ

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
HashSet<User> users = new HashSet<>();
users.add(new User("a@gmail.com"));
users.add(new User("a@gmail.com"));

System.out.println(users.size()); // 1
```

---

# 12. Collision là gì?

Collision là tình huống:

> nhiều phần tử khác nhau nhưng rơi vào cùng một bucket.

Ví dụ:

- 2 object khác nhau có `hashCode()` giống nhau
- hoặc hash khác nhau nhưng sau khi tính index lại cùng rơi vào một bucket

Khi collision xảy ra:

- bucket đó sẽ phải chứa nhiều node
- Java phải duyệt trong bucket để tìm phần tử đúng

---

## 12.1 JDK xử lý collision như thế nào?

### Thời các bản cũ

Bucket chủ yếu là **linked list**.

### Từ JDK 8+

Nếu collision quá nhiều và bucket quá dài, Java có thể chuyển bucket đó thành **red-black tree** để tăng hiệu năng.

Điều này giúp giảm trường hợp xấu khi quá nhiều phần tử dồn vào cùng một bucket.

---

# 13. Resize trong HashSet là gì?

Vì `HashSet` dùng `HashMap`, nên resize thực chất là resize của `HashMap`.

Khi số phần tử vượt ngưỡng:

``` java
threshold = capacity * loadFactor
```

thì `HashMap` sẽ:

- tạo mảng bucket mới lớn hơn
- tính lại vị trí của các node
- chuyển node sang mảng mới

Ví dụ thường gặp:

- capacity ban đầu: `16`
- load factor: `0.75`
- threshold: `12`

Khi thêm phần tử thứ 13 thì sẽ resize.

---

# 14. Vì sao HashSet nhanh?

Vì nó dựa trên hash table.

Trong điều kiện trung bình:

- `add()` -> `O(1)`
- `contains()` -> `O(1)`
- `remove()` -> `O(1)`

So với `List.contains()` phải quét tuần tự `O(n)`, `HashSet` nhanh hơn rõ rệt khi dữ liệu lớn.

---

# 15. Vì sao HashSet cho phép 1 giá trị `null`?

Vì `HashMap` cho phép 1 key là `null`.

Mà `HashSet` lưu phần tử dưới dạng key của `HashMap`, nên nó cũng cho phép 1 phần tử `null`.

``` java
HashSet<String> set = new HashSet<>();
set.add(null);
set.add(null);

System.out.println(set.size()); // 1
```

---

# 16. Các thao tác phổ biến và bản chất bên trong

## 16.1 `add(e)`

``` java
set.add("Java");
```

Bản chất:

``` java
map.put("Java", PRESENT);
```

- chưa có key -> thêm mới -> trả `true`
- đã có key -> không tạo phần tử mới -> trả `false`

---

## 16.2 `contains(o)`

``` java
set.contains("Java");
```

Bản chất:

``` java
map.containsKey("Java");
```

---

## 16.3 `remove(o)`

``` java
set.remove("Java");
```

Bản chất:

``` java
map.remove("Java");
```

---

## 16.4 `size()`

``` java
set.size();
```

Bản chất là lấy số key hiện có trong `HashMap`.

---

# 17. Minh họa bằng ví dụ trực quan

``` java
HashSet<String> set = new HashSet<>();
set.add("A");
set.add("B");
set.add("A");
```

Bên trong gần như:

## Lần 1

``` java
map.put("A", PRESENT);
```

Map hiện tại:

``` java
"A" -> PRESENT
```

## Lần 2

``` java
map.put("B", PRESENT);
```

Map hiện tại:

``` java
"A" -> PRESENT
"B" -> PRESENT
```

## Lần 3

``` java
map.put("A", PRESENT);
```

Do key `"A"` đã tồn tại nên không sinh phần tử mới.

Map vẫn là:

``` java
"A" -> PRESENT
"B" -> PRESENT
```

Cho nên `HashSet` chỉ có 2 phần tử.

---

# 18. Những hiểu lầm rất hay gặp

## 18.1 Nghĩ rằng HashSet có thứ tự

Sai.

`HashSet` không đảm bảo thứ tự.

Muốn giữ thứ tự thêm vào thì dùng `LinkedHashSet`.

---

## 18.2 Nghĩ rằng HashSet có index

Sai.

`HashSet` không có `get(index)`.

---

## 18.3 Nghĩ rằng chỉ cần `hashCode()` là đủ

Sai.

`HashSet` cần cả:

- `hashCode()` để vào bucket
- `equals()` để xác nhận có phải cùng phần tử không

---

## 18.4 Dùng object mutable trong HashSet một cách nguy hiểm

Nếu object đã add vào `HashSet`, rồi bạn sửa field tham gia vào `hashCode()` hoặc `equals()`, object đó có thể bị “lạc
logic” trong cấu trúc hash.

Đây là lỗi rất nguy hiểm.

Ví dụ tư duy:

- add user có email `a@gmail.com`
- hash được tính theo email này
- sau đó đổi email thành `b@gmail.com`
- lúc tìm lại bằng `contains()` có thể phát sinh hành vi rất khó hiểu

=> field dùng trong `equals()` / `hashCode()` nên ổn định.

---

# 19. So sánh HashSet với các Set khác

## 19.1 HashSet

- không trùng
- không đảm bảo thứ tự
- nhanh nhất trong đa số tình huống

## 19.2 LinkedHashSet

- không trùng
- giữ thứ tự thêm vào
- chậm hơn `HashSet` một chút

## 19.3 TreeSet

- không trùng
- tự sắp xếp
- dùng cây đỏ đen
- chậm hơn `HashSet` vì thao tác thường là `O(log n)`

---

# 20. Một mô hình hình dung rất dễ nhớ

## HashMap

Giống như một tủ nhiều ngăn:

- mỗi key được đưa vào một ngăn dựa trên hash
- nếu nhiều key cùng vào một ngăn thì xử lý collision

## HashSet

Là dùng chính cái tủ đó, nhưng:

- chỉ quan tâm key
- value chỉ là một tờ giấy giả `PRESENT`

Nói lại một lần nữa:

> `HashSet` là `HashMap` chỉ dùng phần key.

---

# 21. Kết luận bản chất cốt lõi

## Hãy nhớ 7 ý sau

### 1. `HashSet` là một `Set`

Nó dùng để lưu phần tử không trùng lặp.

### 2. `HashSet` không có index

Không truy cập theo vị trí như `List`.

### 3. `HashSet` không đảm bảo thứ tự

Không được dựa vào thứ tự khi duyệt.

### 4. `HashSet` dùng `HashMap` bên dưới

Đây là bản chất quan trọng nhất.

### 5. Mỗi phần tử của `HashSet` là một key trong `HashMap`

Value chỉ là object giả `PRESENT`.

### 6. Chống trùng nhờ `hashCode()` và `equals()`

Muốn logic đúng với object custom thì phải override đúng.

### 7. `HashSet` rất mạnh ở bài toán check tồn tại và loại bỏ trùng

Đây là use case mạnh nhất của nó.

---

# 22. Ghi nhớ siêu ngắn

- `HashSet` = tập hợp không trùng
- không có index
- không đảm bảo thứ tự
- chạy nhanh nhờ hash
- bên dưới là `HashMap`
- mỗi phần tử set = một key của map
- chống trùng nhờ `hashCode()` + `equals()`

---

# 23. Câu chốt cuối cùng

> `HashSet` nhìn bên ngoài là một tập hợp không trùng lặp, nhưng nhìn từ bên trong thì nó chỉ là một lớp bọc dùng lại bộ
> máy hash table của `HashMap`, trong đó mỗi phần tử được lưu như một key duy nhất.
