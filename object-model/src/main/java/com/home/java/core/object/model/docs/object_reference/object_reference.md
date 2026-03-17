# 1. Object vs Reference

## 1. Object

### 1. Định nghĩa

- Là một **instance** (thực thể cụ thể) của một class
- Chứa: **dữ liệu (fields)** + **hành vi (methods)**.
- **Object**: danh từ chỉ vật thể.
- **Instance**: sự tồn tại cụ thể của một class trong bộ nhớ khi chương trình chạy.
- **Object = State (Trạng thái) + Behavior (Hành vi) + Identity (Danh tính)**
- **Được tạo khi dùng **new****
- Mỗi object được cấp phát bộ nhớ trong heap.

``` java
class Car {
  String model;
  int year;
  void startEngine() {
      System.out.println("Engine started");
  }
}
Car myCar = new Car(); // myCar là một đối tượng của lớp Car
myCar.model = "Toyota";
myCar.year = 2020;
myCar.startEngine(); // Gọi phương thức startEngine() của đối tượng myCar
```

### 2. Thành phần

- State (Trạng thái):
    - Thể hiện qua **fields / variables**
    - Lưu trữ dữ liệu của đối tượng
- Behavior (Hành vi):
    - Thể hiện qua **method**
    - Định nghĩa đối tượng có thể làm gì!
    - Có thể:
        - Đọc state
        - Thay đổi state
- Identity (Danh tính):
    - Mỗi object có một danh tính riêng
    - Cho phép phân biệt các đối tượng khác nhau dù chúng có cùng giá trị dữ liệu
    - Thường được hiểu là địa chỉ bộ nhớ (được trừu tượng hóa bởi JVM)

``` java
class Person {
   String name; // state
   int age;     // state
  
   void speak() { // Behavior
       System.out.println("Hello, my name is " + name);
   }
}
```

``` java
User a = new User("An", 20);
User b = new User("An", 20);
// - `a != b` vì khác identity
// - Dù state giống nhau, vẫn là 2 object khác nhau
```

### 3. Class và Object

| Đặc điểm   | Class                        | Object                      |
|:-----------|------------------------------|-----------------------------|
| Bản chất   | Blueprint (khuôn) - metadata | Thực thể được tạo từ khuôn  |
| Sự tồn tại | Mã nguồn                     | Tồn tại trong Heap khi chạy |
| Số lượng   | 1 class                      | Có thể tạo nhiều object     |
| Bộ nhớ     | Nạp vào Metaspace            | Chiếm RAM trong Heap        |

### 4. Object vs Primitive

- Primitive:
    - Lưu giá trị trực tiếp
    - Không có behavior
- Object:
    - Lưu reference
    - Có method
    - Có identity

### 5. Bản chất bộ nhớ

``` java
 Dog myDog = new Dog();

```

Quy trình:

- `Dog myDog` → tạo biến reference trên **Stack**
- `new` → cấp phát vùng nhớ trên **Heap**
- `Dog()` → gọi constructor
- `myDog` giữ reference trỏ tới object trên Heap
    - 👉 Object nằm trên Heap
    - 👉 Reference nằm trên Stack (nếu là biến local)

### 6. Object vs Primitive

| Primitive | Object  |
|:---------:|:-------:|
|    int    | Integer |
|  double   | Double  |
|  boolean  | Boolean |

- Primitive:
    - Lưu giá trị trực tiếp
    - Không có behavior
- Object:
    - Lưu reference
    - Có method
    - Có identity

### 7. Lifecycle của Object (Dừng ở mức biết đến)

1. Allocation (new)
2. Use
3. Eligible for GC
4. Garbage Collected
    - Object chết khi: Không còn reference trỏ tới nó

## 2. Reference (Tham chiếu)

1. Định nghĩa: Biến lưu chữ địa chỉ vùng nhớ dẫn đến chỗ của Object trong Heap (Nơi đối tượng được lưu trữ)

    - Reference: Một reference
    - Biến dùng để trỏ đến một object trong bộ nhớ.
    - Khi khai báo một biến object -> đang tạo một reference trỏ đến một đối tượng trên heap.
    - Reference chỉ chứa địa chỉ của object, chứ không phải dữ liệu của nó.
      ``` Java
      MyClass obj1 = new MyClass();
      // obj1 là reference, đối tượng MyClass được tạo trong heap
      ```

2. Đặc tính:
    - Tách biệt hoàn toàn: nằm ở Stack (nếu là biến cục bộ)
        - Object ở Heap
        - Reference = null bẻ gãy "sợi dây liên kết" đến Object
    - Tính đa trỏ(Aliasing): Nhiều Reference có thể cùng trỏ về 1 Object (Địa chỉ)
        - Nếu dùng **refA** để thay đổi thuộc tính Object -> **refB** cũng thấy sự thay đổi đó
    - Kích trước cố định: "biến Reference" chứa địa chỉ luôn có kích thước cố định (4 or 8 byte) tùy hệ điều hành 32
      or 64
3. Reference vs Primitive

| Đặc điểm            | Primitive                      | Reference                                             |
|---------------------|:-------------------------------|-------------------------------------------------------|
| Dữ liệu lưu trữ     | Giá trị thực (ví dụ: 5, true). | Địa chỉ bộ nhớ (ví dụ: 0x1A2B).                       |
| Vùng nhớ            | Stack                          | Stack (biến) và Heap (đối tượng).                     |
| Giá trị mặc định    | Không bao giờ là null.         | Mặc định là null nếu chưa gán.                        |
| Khi dùng toán tử == | So sánh giá trị thực sự.       | So sánh địa chỉ (hai biến có trỏ cùng một nơi không). |

4. Tham chiếu Null
    - null reference là một tham chiếu không trỏ tới bất kỳ đối tượng nào trong bộ nhớ.
    - NullPointerException

### 3. Điểm quan trọng:

- Khi bạn gán một reference cho một reference khác, bạn không sao chép object, mà chỉ sao chép địa chỉ bộ nhớ (tức
  là cả hai reference sẽ trỏ đến cùng một object).
- Pass-by-Reference: Khi bạn truyền một reference vào phương thức, bạn truyền địa chỉ của object, không phải object
  thực tế.

# 2. Heap vs Stack

## 1. Stack:

- Lưu trữ biến cục bộ + reference
- Truy cập nhanh.
- **Khu vực bộ nhớ được quản lý tự động.**
- Stack có tính năng tự động giải phóng bộ nhớ khi các phương thức hoàn tất. Stack quản lý bộ nhớ theo dạng
  **LIFO (Last In, First Out)**.

## 2. Heap:

- Lưu trữ all Object
- Là khu vực bộ nhớ được quản lý thủ công hoặc bởi Garbage Collector.
- Các đối tượng trong Java (và hầu hết các ngôn ngữ OOP) được tạo và quản lý trong heap. Bộ nhớ trong heap cần được
  phân bổ và giải phóng, thường
  thông qua Garbage Collection (GC).

| **Đặc điểm**                | **Heap**                                                                                           | **Stack**                                                              |
|-----------------------------|:---------------------------------------------------------------------------------------------------|------------------------------------------------------------------------|
| **Vị trí lưu trữ**          | Object + mảng (array).                                                                             | Lưu trữ các biến cục bộ (local variables) và tham chiếu đến object.    |
| **Quản lý bộ nhớ**          | Garbage Collector.                                                                                 | Quản lý tự động bởi hệ thống, bộ nhớ được giải phóng khi hàm kết thúc. |
| **Kích thước**              | Kích thước lớn, bộ nhớ có thể thay đổi động.                                                       | Kích thước nhỏ và cố định.                                             |
| **Thời gian sống**          | Object sống lâu dài trong bộ nhớ cho đến khi không còn tham chiếu, được Garbage Collector thu dọn. | Biến cục bộ chỉ sống trong phạm vi phương thức.                        |
| **Tốc độ truy cập**         | Chậm hơn vì phải quản lý bộ nhớ động.                                                              | Nhanh hơn, truy cập trực tiếp vào bộ nhớ.                              |
| **Tính chất**               | Được sử dụng cho các đối tượng có kích thước thay đổi (dynamic allocation).                        | Dùng cho các biến nhỏ, tạm thời, không thay đổi.                       |
| **Khả năng quản lý bộ nhớ** | Quản lý phức tạp, cần Garbage Collector.                                                           | Quản lý đơn giản, tự động giải phóng bộ nhớ khi phương thức kết thúc.  |

# 3. Final Keyword

- Final variable: Khi một biến được khai báo với từ khóa final, nó không thể thay đổi giá trị sau khi được khởi tạo.
  Đối với primitive, giá trị không thay đổi, còn đối với reference, địa chỉ của object không thể thay đổi, nhưng nội
  dung của object vẫn có thể thay đổi.
- Final method: Không thể override method này trong lớp con.
- Final class: Không thể kế thừa từ lớp này.

``` java
final int x = 10; // Không thể gán lại giá trị cho x
```

- Điểm quan trọng:
    - Immutability: Lớp có thể được khai báo là final để không bị kế thừa và thay đổi. Điều này giúp đảm bảo tính toàn
      vẹn của dữ liệu trong concurrency (đa luồng).

# 4. Immutable Objects và Benefits trong Concurrency

- Immutable Objects: Một object immutable là object mà không thể thay đổi sau khi nó được tạo. Khi bạn tạo một đối
  tượng immutable, bạn không thể thay đổi giá trị của các thuộc tính của nó sau khi đối tượng đã được khởi tạo. Các lớp
  String trong Java là ví dụ điển hình.

- Điểm quan trọng:
    - Thread-Safety: Vì immutable object không thể thay đổi sau khi được tạo, chúng rất hữu ích trong môi trường
      concurrency, nơi mà nhiều thread có thể cùng truy cập vào dữ liệu mà không lo xảy ra xung đột hay thay đổi không
      mong muốn.
    - Sử dụng Final Keyword: Các thuộc tính của immutable object thường được khai báo là final để đảm bảo chúng không
      thể thay đổi sau khi đối tượng đã được khởi tạo.
      • Tối ưu hóa Garbage Collection: Vì immutable objects không thay đổi, hệ thống có thể tối ưu hóa việc thu gom
      rác (Garbage Collection) bằng cách chia sẻ đối tượng giữa các thread thay vì tạo bản sao mới.
    ```
    public final class ImmutablePerson {
    private final String name;
    private final int age;
    
        public ImmutablePerson(String name, int age) {
            this.name = name;
            this.age = age;
        }
        public String getName() {
            return name;
        }
        public int getAge() {
            return age;
        }
    }
    ```

- Lợi ích trong Concurrency:
    - Không cần đồng bộ hóa: Các object immutable có thể được chia sẻ giữa các thread mà không cần phải đồng bộ hóa, vì
      không thể thay đổi trạng thái của chúng.
    - Giảm độ phức tạp và sai sót: Không phải lo lắng về vấn đề race conditions khi các thread cùng sửa đổi cùng một
      object,
      vì immutable object không thể bị thay đổi.

-----
Để đạt được level senior:

- Cách các object và reference hoạt động.
- Quản lý bộ nhớ trong stack và heap.
- Sử dụng từ khóa final để bảo vệ giá trị và tránh sự thay đổi không mong muốn.
- Hiểu và áp dụng các immutable objects trong lập trình đồng thời (concurrency), giúp tránh các lỗi liên quan đến đồng
  bộ hóa và tối ưu hóa hiệu suất.
