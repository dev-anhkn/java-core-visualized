# java-core-visualized
java-core-visualized

## 1. Phạm vi **Mid Java Core – nên nắm chắc**

### ✅ BẮT BUỘC (Core Mid)
* **01-object-model-fundamentals**

    * Pass-by-value với object
    * Shallow vs deep copy
    * Serialization dùng trong thực tế (JSON)
    * 
      * 01-object-model-fundamentals
        ├─ primitiveDemo
        ├─ objectMutateDemo
        ├─ reassignDemo
        ├─ boxDemo
        ├─ wrapperDemo
        ├─ stringDemo
        ├─ arrayMutateDemo          🔥
        ├─ collectionMutateDemo     🔥
        ├─ cloneShallowDemo         🔥
        ├─ equalsVsDoubleEqualsDemo 🔥
        ├─ immutableObjectDemo      🔥
        └─ jsonSerializationDemo    (transient, date, circular)


* **02-oop-advanced-design**

    * Composition > Inheritance
    * Interface default methods
    * Sealed class: *biết dùng, chưa cần thiết kế*

* **03-immutability-data-carriers**

    * Immutability “effective”
    * Records: dùng để thay DTO

* **04-equality-comparisons**

    * equals / hashCode chuẩn
    * Comparator vs Comparable

* **05-collections-framework-internals**

    * HashMap resize, collision
    * Khi nào dùng List / Set / Map
    * ConcurrentHashMap: **không synchronized toàn bảng**

* **06-generics-type-system**

    * Wildcard cơ bản (extends / super)
    * Tránh raw type

---

### ✅ CONCURRENCY – MỨC MID (RẤT QUAN TRỌNG)

* **10-concurrency-foundation**

    * synchronized vs volatile (biết khi nào dùng)
    * AtomicInteger là gì
    * Happens-before (mức concept)

* **11-modern-concurrency-high-scale**

    * ThreadPoolExecutor
    * Fixed vs Cached pool
    * CompletableFuture cơ bản

👉 **Chưa cần**: Virtual Threads, Structured Concurrency

---

### ✅ I/O – JVM – SYNTAX (BIẾT DÙNG)

* **08-jvm-architecture-tuning**

    * Heap / Stack / Metaspace
    * GC là gì, vì sao Full GC chậm
    * Xmx / Xms

* **13-io-nio-networking**

    * Blocking vs non-blocking (ý tưởng)
    * HTTP Client Java 11

* **14-functional-programming-deep-dive**

    * Lambda syntax
    * Stream map / filter / collect
    * Không lạm dụng stream

* **15-modern-syntax-sugar**

    * var
    * instanceof pattern
    * switch mới

---

### 🟡 BIẾT LÀ LỢI THẾ (NHƯNG CHƯA BẮT BUỘC)

* **07-exception-handling-reliability**
* **09-dynamic-java**
* **16-performance-profiling-tools**
* **17-design-pattern-core-best-practices**

---

## 2. Những thứ **Mid KHÔNG cần đào sâu**

Để tránh “học quá đà”:

* ❌ JIT C1/C2 chi tiết
* ❌ GC tuning nâng cao
* ❌ Java Agent / Bytecode
* ❌ ClassLoader custom
* ❌ Memory barrier cấp CPU
* ❌ Virtual Thread internals

---

## 3. Checklist tự đánh giá: **đã đạt Mid chưa?**

Bạn **đạt Mid Java Core** khi:

* Viết `equals/hashCode` không lỗi
* Biết vì sao `HashMap` không thread-safe
* Biết tránh mutable shared state
* Dùng ThreadPool thay vì `new Thread`
* Đọc hiểu code có generics + lambda
* Debug được lỗi concurrent đơn giản (race condition nhẹ)

---

## 4. Kết luận thẳng thắn

* Cấu trúc của bạn: **Mid+ (rất tốt)**
* Học đến mức trên: **đủ đi làm vững**, không bị “hổng core”
* Khi nào bạn cảm thấy:

  > “Code của người khác mình đọc vẫn hơi mơ hồ”

  → lúc đó **nâng lên Senior là đúng thời điểm**

Khi bạn sẵn sàng:

* Tôi có thể **đóng gói lại thành roadmap 8–10 tuần cho Mid**
* Hoặc **mỗi module → 5 câu hỏi phỏng vấn Mid**
* Hoặc **mini project Java Core tổng hợp**

Bạn cứ xem trước, chưa cần vội.


OK, đã **hiểu đúng ý** rồi 👍
Ta **bỏ hết phần nói nhiều**, **không nói kiến trúc**, **không nói lý thuyết suông**.
Chỉ **gạch đầu dòng cho một project CODE thuần Java Core (Java 25)**, với mục tiêu:

> Đọc lại CODE → tưởng tượng được JVM đang làm gì → đi phỏng vấn NÓI ĐƯỢC
> LOG là phần **trung tâm**, không phải phụ trợ.

---

# 🎯 Mục tiêu project (chốt lại)

* 100% **Java Core bằng code**
* Code **ngắn – rõ – có log dẫn chuyện**
* Mỗi class = **1 ý cốt lõi**
* Đọc code giống như **xem lại một bài giảng có hình ảnh trong đầu**

---

# 📦 Project: `java-core-visualized`

---

# 🗂️ Cấu trúc project (code-oriented)

```
java-core-visualized
│
├─ logging
│   ├─ logback.xml
│   └─ Log.java
│
├─ objectmodel
├─ oop
├─ immutability
├─ equals_hashcode
├─ collections
├─ generics
├─ exception
├─ jvm
├─ classloading
├─ concurrency_basic
├─ concurrency_advanced
├─ streams
├─ datetime
└─ Main.java
```

---

# 🔧 LOG – nguyên tắc xuyên suốt (RẤT QUAN TRỌNG)

* Mỗi ví dụ **phải log được câu chuyện runtime**
* Log luôn có:

    * Thread
    * Thời điểm
    * Ý nghĩa hành động

👉 Log không chỉ in kết quả, **log để giải thích chuyện gì đang xảy ra**

---

# 🧠 Các module – gạch đầu dòng đúng trọng tâm

---

## 1️⃣ objectmodel – Java làm việc với object thế nào

**Code thể hiện**

* Stack frame
* Heap object
* Reference copy
* Mutate vs reassign

**Log bắt buộc**

* identityHashCode
* Trước / sau method call
* “reference copied, object not copied”

👉 Đọc lại là **hết nhầm pass-by-reference**

---

## 2️⃣ oop – kế thừa & đa hình (runtime binding)

**Code thể hiện**

* Overload vs Override
* Method gọi theo runtime type
* Field không đa hình
* Constructor chain

**Log**

* Class runtime thực sự
* Method nào được gọi
* Thứ tự constructor

👉 Phỏng vấn hỏi polymorphism → **kể theo log**

---

## 3️⃣ immutability – object bất biến

**Code thể hiện**

* Immutable class đúng chuẩn
* Defensive copy
* String behavior

**Log**

* Object hash không đổi
* Thread khác đọc cùng object
* Không cần synchronized

👉 Gắn với **thread-safety tự nhiên**

---

## 4️⃣ equals_hashcode – contract sống còn

**Code thể hiện**

* equals đúng / sai
* hashCode ảnh hưởng HashMap
* Bug overwrite key

**Log**

* hashCode từng key
* equals được gọi khi nào

👉 Trả lời được: *“vì sao Map lỗi?”*

---

## 5️⃣ collections – bản chất collection

**Code thể hiện**

* ArrayList resize
* HashMap collision
* ConcurrentHashMap khác HashMap
* Iterator fail-fast

**Log**

* size / threshold
* resize moment
* concurrent access

👉 Dễ tưởng tượng **bên trong container**

---

## 6️⃣ generics – compile-time vs runtime

**Code thể hiện**

* Type erasure
* extends / super
* Generic không tồn tại runtime

**Log**

* Runtime class thật
* Generic bị erase

👉 Không còn mơ hồ wildcard

---

## 7️⃣ exception – luồng thực thi

**Code thể hiện**

* Checked vs unchecked
* finally luôn chạy?
* try-with-resources
* Suppressed exception

**Log**

* Thứ tự chạy thực tế
* Exception bị nuốt

👉 Dùng để **giải thích flow**, không học vẹt

---

## 8️⃣ jvm – bộ nhớ & lifecycle

**Code thể hiện**

* Object sống / chết
* Reference giữ object
* Memory leak kiểu Java

**Log**

* Object create
* Object survive
* Gắn timestamp để soi GC

👉 Phỏng vấn JVM không nói chung chung

---

## 9️⃣ classloading – nền của framework

**Code thể hiện**

* ClassLoader hierarchy
* Load class bằng tay
* NoClassDef vs ClassNotFound

**Log**

* ClassLoader load class
* Parent delegation

👉 Hiểu Spring từ gốc

---

## 🔟 concurrency_basic – thread & lock

**Code thể hiện**

* Race condition
* synchronized lock object
* volatile visibility

**Log**

* Thread name
* Vào / ra critical section
* Giá trị bị race

👉 Nhìn log là thấy bug

---

## 1️⃣1️⃣ concurrency_advanced – hiệu năng

**Code thể hiện**

* ThreadPoolExecutor
* Queue đầy
* Deadlock

**Log**

* Active thread
* Queue size
* Task delay

👉 Trả lời được câu: *“vì sao system chậm”*

---

## 1️⃣2️⃣ streams – functional style

**Code thể hiện**

* Lazy evaluation
* map / filter / reduce
* Parallel stream pitfall

**Log**

* peek chỉ chạy khi terminal
* Thứ tự xử lý

👉 Không còn học stream theo cảm giác

---

## 1️⃣3️⃣ datetime – thời gian & timezone

**Code thể hiện**

* Instant vs LocalDateTime
* ZoneId
* Bug timezone

**Log**

* UTC vs local time
* Convert time

---

# 🧩 Main.java

* Chỉ để chạy từng demo
* Mỗi demo in header log rõ ràng

---

# ✅ Khi project này hoàn thành

* Đọc code = **xem lại bản đồ Java Core**
* Phỏng vấn:

    * Không nói “em nghĩ là…”
    * Nói: *“em từng log ra thấy…”*
* Java không còn trừu tượng

---

## 👉 Bước tiếp theo (rất nên làm)

Tôi có thể:

1. **Chọn 5 module quan trọng nhất để viết code mẫu trước**
2. Viết **1 module hoàn chỉnh (code + log) làm chuẩn**
3. Chuyển toàn bộ thành **cheat-sheet phỏng vấn dựa trên code**

Bạn chọn **1** hướng, tôi làm tiếp ngay.
