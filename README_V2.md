# Java Core – Roadmap (Middle Level)

## Tuần 1 – Nền tảng cơ bản của Java

### Day 1: Object Model & Memory Management
- Object vs reference
- Heap vs Stack
- Final keyword
- Immutable objects và benefits trong concurrency

### Day 2: `equals()` và `hashCode()`
- Contract của `equals()` và `hashCode()`
- Tạo class có custom `equals()`/`hashCode()`
- Testing: HashMap key, Set

### Day 3: Collections cơ bản
- ArrayList, LinkedList, HashMap
- Hiểu cách hoạt động cơ bản của Collections
- Khi nào dùng các Collections nào (List, Set, Map)

### Day 4: Exception handling
- Checked vs Unchecked exceptions
- Throw, throws, try-catch
- Best practices: tạo custom exceptions

### Day 5: Static và final
- Static field và method
- Final variable, class, method
- Cách sử dụng đúng `static` và `final` trong các trường hợp thực tế

---

## Tuần 2 – OOP & Design Patterns cơ bản

### Day 6: Inheritance & Polymorphism
- Lý thuyết kế thừa (extends), Overriding và Overloading
- Use cases của polymorphism
- `super`, `this`, `instanceof`

### Day 7: Encapsulation & Abstraction
- Access modifiers (private, public, protected)
- Getter/Setter
- Abstract class và Interface

### Day 8: Constructor, Singleton & Factory patterns
- Constructor overloading
- Singleton pattern (Eager vs Lazy loading)
- Factory pattern: Basic implementation

### Day 9: Command và Strategy patterns
- Basic của Command pattern
- Strategy pattern trong thực tế (thực hành với một số use case)

### Day 10: Clean Code (tạo code dễ đọc và maintainable)
- Các nguyên tắc viết code sạch (naming, comment, method length)
- Refactoring basic (đơn giản hoá code, giảm complexity)

---

## Tuần 3 – Java Collections và Streams

### Day 11: List, Set, Map (so sánh và ứng dụng)
- Hiểu sự khác biệt giữa `ArrayList`, `LinkedList`, `HashSet`, `HashSet` và `HashMap`
- Khi nào nên dùng loại nào trong thực tế
- Thực hành với các collections

### Day 12: Java Streams (Basic)
- Sử dụng Stream API cho Collection (filter, map, reduce)
- `forEach`, `collect`, `filter`, `map` trong Stream
- Phân biệt giữa `forEach` và `map`

### Day 13: Stream nâng cao & Optional
- Chaining methods trong Stream
- Optional class để xử lý null
- Tạo các sử dụng Optional đúng cách

### Day 14: Lambda và Functional Interfaces
- Viết lambda expressions
- Sử dụng `Predicate`, `Function`, `Consumer`, `Supplier`
- Khi nào dùng lambda thay vì anonymous class

### Day 15: Comparator vs Comparable
- Cách implement `Comparable` và `Comparator`
- Ví dụ về sorting object theo nhiều tiêu chí

---

## Tuần 4 – Concurrency & Performance Tuning

### Day 16: Thread basics và synchronization
- Tạo và start Thread
- Sử dụng `synchronized` để tránh race condition
- `Thread` và `Runnable` interface

### Day 17: ExecutorService & Concurrency Utilities
- Sử dụng `ExecutorService`, `ScheduledExecutorService`
- `CountDownLatch`, `CyclicBarrier`
- Các công cụ concurrency hỗ trợ như `Semaphore`

### Day 18: Memory Management trong Java
- Cách Java quản lý memory (heap, stack, GC)
- Thực hành với bộ nhớ trong JVM
- Cách kiểm tra memory leaks và tối ưu bộ nhớ

### Day 19: Performance Tuning
- Cách benchmark code đúng cách (JUnit, micro-benchmark)
- Tuning Collection performance (hashing, load factor)
- Cải thiện performance với caching

### Day 20: Debugging và Profiling
- Sử dụng công cụ debug trong IDE (breakpoints, watches)
- Profiling code để tìm bottleneck với VisualVM

---

## Tuần 5 – Tính năng nâng cao (Advanced Java)

### Day 21: Reflection
- Cách sử dụng reflection trong Java
- Tạo object và gọi method qua reflection
- Khi nào tránh dùng reflection

### Day 22: Java NIO (New I/O)
- NIO vs IO (Sự khác biệt)
- Cách sử dụng `FileChannel`, `Buffer`, `Selector`
- Thực hành với NIO để đọc/ghi file nhanh

### Day 23: Serialization & Deserialization
- Lý thuyết và thực hành với `Serializable`
- Khi nào dùng và không dùng serialization
- Cải thiện hiệu năng serialization

### Day 24: Java 8+ Features
- Cập nhật các tính năng mới từ Java 8 trở lên (default methods, `java.time`, etc.)
- Làm quen với các library hữu ích từ JDK

### Day 25: Testing trong Java (JUnit 5)
- Unit test cơ bản với JUnit 5
- Mocking với Mockito
- Best practices trong việc viết test

---

## Tuần 6 – Ôn tập và Mini Project

### Day 26: Mini project - Build a simple Banking System
- Dùng tất cả các kỹ năng học được để xây dựng một hệ thống ngân hàng đơn giản.
- Implement: Open account, Deposit, Withdraw, Check balance, etc.

### Day 27: Final project: Build a CRUD application
- Xây dựng ứng dụng CRUD đơn giản với các tính năng tìm kiếm, phân trang, filter, sort.
- Áp dụng mọi kiến thức từ collections, concurrency đến testing.

---