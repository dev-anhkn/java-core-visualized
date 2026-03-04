
# Object vs Reference in Java - Questions and Answers

### 1. **Sự khác biệt giữa Object và Reference trong Java là gì?**
**Object** là một thực thể (instance) của một **class**, chứa dữ liệu và hành vi. **Reference** là một biến tham chiếu đến địa chỉ của một **Object** trong bộ nhớ.

- **Object**: Lưu trữ dữ liệu thực tế và hành vi (method). Được cấp phát bộ nhớ trong **heap**.
- **Reference**: Chỉ lưu trữ **địa chỉ bộ nhớ** của một đối tượng, được cấp phát trong **stack**.

### 2. **Khi nào Java cấp phát bộ nhớ cho một Object trong heap?**
Khi bạn sử dụng từ khóa `new` để tạo ra một đối tượng mới, JVM sẽ cấp phát bộ nhớ trong **heap** để lưu trữ đối tượng đó. Nếu đủ bộ nhớ, địa chỉ của đối tượng được lưu trữ trong **reference variable**.

### 3. **Khi một reference được gán giá trị `null`, điều gì xảy ra với object mà nó trỏ tới?**
Khi reference được gán giá trị `null`, nó không còn trỏ đến bất kỳ object nào. Nếu trước đó có đối tượng được tham chiếu, và nếu không còn reference nào trỏ đến nó, đối tượng đó sẽ trở thành **reachable** cho Garbage Collector và sẽ bị thu gom khi có đủ bộ nhớ.

### 4. **Giải thích về khái niệm shallow copy và deep copy. Tác động của chúng đối với Object và Reference là gì?**
- **Shallow copy**: Tạo bản sao của **object** nhưng chỉ sao chép **reference** (không sao chép đối tượng gốc). Thay đổi đối tượng sao chép sẽ ảnh hưởng đến đối tượng gốc.
- **Deep copy**: Tạo bản sao hoàn toàn của **object** và các đối tượng liên quan. Thay đổi bản sao không ảnh hưởng đến đối tượng gốc.

### 5. **Khi một method nhận vào một tham số là reference, liệu việc thay đổi Object bên trong method có ảnh hưởng đến Object ban đầu không?**
Có, nếu bạn thay đổi đối tượng thông qua **reference** trong method, thay đổi đó sẽ ảnh hưởng đến **object** ban đầu, vì cả hai đều trỏ đến cùng một vùng nhớ trong **heap**.

### 6. **Giải thích sự khác biệt giữa `==` và `equals()` khi so sánh hai reference trong Java.**
- **`==`**: So sánh địa chỉ bộ nhớ của hai reference, tức là kiểm tra xem chúng có trỏ đến cùng một đối tượng hay không.
- **`equals()`**: So sánh giá trị nội tại của các đối tượng (nếu được ghi đè), kiểm tra xem các đối tượng có giống nhau về dữ liệu hay không.

### 7. **Làm thế nào để đảm bảo rằng một reference không trỏ đến một Object đã bị thu gom bởi Garbage Collector?**
Không thể chắc chắn ngay lập tức khi nào Garbage Collector sẽ thu gom đối tượng. Tuy nhiên, bạn có thể sử dụng **weak references** để tránh giữ lại các đối tượng không còn cần thiết, giúp Garbage Collector thu gom chúng dễ dàng hơn.

### 8. **Object Immutability: Tại sao immutable objects không bị thay đổi khi có nhiều reference trỏ tới cùng một Object?**
Các **immutable objects** không thể thay đổi trạng thái sau khi được tạo ra. Do đó, bất kỳ **reference** nào trỏ tới đối tượng này đều không thể thay đổi dữ liệu của nó, giúp bảo vệ tính toàn vẹn của dữ liệu trong môi trường đa luồng.

### 9. **Khi một Object không còn được tham chiếu bởi bất kỳ reference nào, làm thế nào Garbage Collector sẽ quyết định khi nào thu gom bộ nhớ?**
Garbage Collector sẽ quyết định thu gom một object khi nó không còn **reachable** từ bất kỳ reference nào. Việc thu gom có thể không xảy ra ngay lập tức mà có thể bị trì hoãn tùy thuộc vào chiến lược Garbage Collection của JVM.

### 10. **Khi tạo một object trong Java, reference trỏ đến object đó được lưu ở đâu trong bộ nhớ?**
**Reference** được lưu trữ trong **stack**, trong khi **object** thực tế được lưu trong **heap**. Reference lưu trữ địa chỉ bộ nhớ của object trong heap.

### 11. **Làm thế nào để bạn phân biệt giữa một reference trỏ đến một Object trong heap và một biến primitive lưu trữ giá trị trực tiếp?**
**Primitive variables** lưu trữ giá trị trực tiếp trong bộ nhớ, trong khi **reference variables** lưu trữ địa chỉ bộ nhớ (địa chỉ của object) trong stack. Đối với các reference, bạn có thể truy cập vào dữ liệu object thông qua địa chỉ của nó.

### 12. **Giải thích sự khác biệt giữa mutable objects và immutable objects khi làm việc với reference.**
- **Mutable objects** có thể thay đổi trạng thái sau khi tạo ra. Việc thay đổi các mutable objects sẽ ảnh hưởng đến tất cả các reference trỏ đến chúng.
- **Immutable objects** không thể thay đổi sau khi tạo, giúp tránh các vấn đề như **race condition** trong môi trường đa luồng.

### 13. **Reference variables và Static variables có sự khác biệt gì về cách quản lý bộ nhớ trong Java?**
- **Reference variables** lưu trữ địa chỉ của object trong **stack**. Các reference variables này sẽ được giải phóng khi ra khỏi phạm vi hàm.
- **Static variables** lưu trữ trong **heap** và tồn tại suốt vòng đời của ứng dụng. Chúng không bị Garbage Collector thu gom cho đến khi ứng dụng dừng lại.

### 14. **Khi nào bạn nên sử dụng kiểu dữ liệu reference thay vì kiểu dữ liệu primitive trong Java?**
Bạn nên sử dụng **reference types** khi cần lưu trữ **null** hoặc khi muốn làm việc với các đối tượng phức tạp hơn (ví dụ: các **object** từ class, hoặc **collections**). Sử dụng **primitive types** khi bạn chỉ cần lưu trữ giá trị cơ bản (int, char, boolean, v.v.).

### 15. **Khi một Object được tạo ra trong Heap, reference trỏ tới Object đó được lưu ở đâu trong bộ nhớ?**
**Reference** được lưu trong **stack**, trong khi **object** được tạo ra trong **heap**. **Reference** chỉ lưu trữ địa chỉ của đối tượng trong heap, giúp bạn truy cập và thao tác với đối tượng đó.

