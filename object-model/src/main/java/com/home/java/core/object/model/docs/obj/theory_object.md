# Object trong Java – Foundation Notes

---

# 1. Object là gì?

## Định nghĩa
```
> Object là một **instance** (thực thể cụ thể) của một class.
```

* **Object**: danh từ chỉ vật thể.
* **Instance**: sự tồn tại cụ thể của một class trong bộ nhớ khi chương trình chạy.

### Định nghĩa “bỏ túi”

> **Object = State (Trạng thái) + Behavior (Hành vi) + Identity (Danh tính)**

> **Được tạo khi dùng **new****

---

# 2. Thành phần của Object

## 2.1 State (Trạng thái)

* Thể hiện qua **fields / variables**
* Lưu trữ dữ liệu của đối tượng

```java
class User {
    String name;
    int age;
}
```

Ví dụ:

```java
User u = new User();
u.name ="An";
u.age =20;
```

State của `u`:

```
name = "An"
age = 20
```

---

## 2.2 Behavior (Hành vi)

* Thể hiện qua **method**
* Định nghĩa đối tượng có thể làm gì

```java
void speak() {
    System.out.println("Hello");
}
```

Behavior có thể:

* Đọc state
* Thay đổi state

---

## 2.3 Identity (Danh tính)

* Mỗi object có một danh tính riêng
* Thường được hiểu là địa chỉ bộ nhớ (được trừu tượng hóa bởi JVM)

```java
User a = new User("An", 20);
User b = new User("An", 20);
```

* `a != b` vì khác identity
* Dù state giống nhau, vẫn là 2 object khác nhau

Identity liên quan đến:

* Reference
* Toán tử `==`
* `hashCode()` mặc định

---



---

# 4. Bản chất bộ nhớ

```java
Dog myDog = new Dog();
```

Quy trình:

1. `Dog myDog` → tạo biến reference trên **Stack**
2. `new` → cấp phát vùng nhớ trên **Heap**
3. `Dog()` → gọi constructor
4. `myDog` giữ reference trỏ tới object trên Heap

- 👉 Object nằm trên Heap
- 👉 Reference nằm trên Stack (nếu là biến local)

---

# 5. Object vs Primitive

| Primitive | Object  |
|-----------|---------|
| int       | Integer |
| double    | Double  |
| boolean   | Boolean |

Primitive:

* Lưu giá trị trực tiếp
* Không có behavior

Object:

* Lưu reference
* Có method
* Có identity

---

# 6. Lifecycle của Object (Dừng ở mức biết đến)

1. Allocation (new)
2. Use
3. Eligible for GC
4. Garbage Collected

Object chết khi:

* Không còn reference trỏ tới nó

---

# Kết luận mức hiện tại

Ở mức kiến thức này, đã nắm được:

* Object là gì
* State / Behavior / Identity
* Reference vs Object
* Heap vs Stack cơ bản
* Lifecycle cơ bản

---

