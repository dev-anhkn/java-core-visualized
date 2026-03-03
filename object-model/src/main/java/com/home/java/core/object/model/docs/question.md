# Bộ câu hỏi kiểm tra hiểu sâu (Level 1 → Level 3)

## Level 1 – Hiểu cơ bản

1. Object khác Class ở điểm nào bản chất nhất?
2. State của object được lưu ở đâu?
3. Vì sao hai object có cùng dữ liệu vẫn khác nhau?
4. Behavior có thể thay đổi state không? - Có, và là cách duy nhất (vì field nên được private theo encapsulation)
5. Primitive có identity không?

---

## Level 2 – Hiểu reference

6. Trong câu lệnh `User u = new User();` thì object nằm ở đâu? - JVM cấp phát mem trên heap
7. Biến `u` chứa cái gì?
8. Khi viết `User u2 = u;` có bao nhiêu object được tạo?
9. Khi `u = null;` thì điều gì xảy ra với object? - làm mất 1 reference
10. Khi method kết thúc, biến local đi đâu?

---

## Level 3 – Hiểu bản chất hơn

11. Nếu hai reference trỏ cùng một object, thay đổi state qua một reference có ảnh hưởng reference kia không?
12. Vì sao mutable object dễ gây bug? - tìm hiểu thêm ở concurrency
13. Vì sao immutable object an toàn hơn trong multi-thread? tìm hiểu thêm ở concurrency
14. Khi object không còn reachable từ GC Root thì điều gì xảy ra?
15. Vì sao static field có thể gây memory leak?

-------

- L1.1:
    - Class là metadata mô tả cấu trúc hành vi
    - object thực thể runtime chưa state cụ thể dựa trên metadata đó
    - 📘 Mức mô hình OOP (design level)
        - Object = Dữ liệu + Hành vi + Danh tính
        - Đây là cách tư duy khi thiết kế hệ thống.

    - ⚙ Mức runtime (JVM level)
        - Object = Object Header (identity info) + Instance Data (state)
        - Behavior = nằm trong Class metadata
- L1.2:
    - State của object nằm trong Heap (trong object đó)
    - field là primitive: nằm trực tiếp bên trong object trên Heap
    - field là object khác:
        - (reference) nằm trong object trên Heap
        - Object tổng giữ reference trỏ tới field (Object)
- L1.3: Mỗi lần new → JVM tạo một thực thể mới với identity riêng.
- L2.6: new User()
    - JVM cấp phát memory trên Heap
    - Tạo object
    - Trả về một reference
  > Object nằm trên Heap