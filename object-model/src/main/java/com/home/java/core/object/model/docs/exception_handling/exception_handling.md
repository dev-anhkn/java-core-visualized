## Day 4 - Exception Handling nhìn theo kiểu 360 độ

Hôm nay không chỉ học cú pháp, mà phải hiểu **bản chất của cơ chế xử lý lỗi trong Java**:
Exception không phải để “chữa cháy”, mà là **cách chương trình mô tả, truyền và kiểm soát trạng thái lỗi**.

---

# 1. Bản chất của Exception là gì

## 1.1 Exception sinh ra để giải quyết vấn đề gì

Nếu không có exception, method thường phải trả về:

* `null`
* `true/false`
* mã lỗi như `-1`, `500`, `ERROR_CODE_X`

Cách này có vấn đề:

* làm mờ nghĩa return thật của method
* caller dễ quên check lỗi
* khó truyền đủ ngữ cảnh lỗi
* code bị rối vì check thủ công ở mọi nơi

Exception ra đời để:

* tách **luồng bình thường** và **luồng lỗi**
* ép hệ thống có cơ chế truyền lỗi lên tầng trên
* giữ được thông tin lỗi: loại lỗi, message, nguyên nhân gốc, stack trace

## 1.2 Exception bản chất là object

Trong Java, exception không phải keyword đặc biệt, mà là **object** kế thừa từ `Throwable`.

Điều này rất quan trọng:

* nó có type
* nó có field
* nó có constructor
* nó có stack trace
* nó có thể wrap exception khác

Tức là exception là **một dữ liệu mô tả lỗi**, chứ không chỉ là “chương trình bị đỏ”.

---

# 2. Luồng chạy thực sự khi có Exception

## 2.1 Cơ chế xảy ra lỗi

Khi một exception bị `throw`:

* JVM dừng luồng thực thi bình thường tại vị trí đó
* tìm `catch` phù hợp gần nhất trong call stack
* nếu không có ai bắt, exception đi ngược dần lên trên
* đến `main` mà vẫn không ai bắt thì thread kết thúc

Đây gọi là **stack unwinding**.

## 2.2 Hiểu call stack ở đây

Ví dụ:

* `main()` gọi `service()`
* `service()` gọi `repository()`
* `repository()` gây lỗi

Nếu `repository()` không catch:

* exception đẩy lên `service()`
* `service()` không catch -> đẩy lên `main()`
* `main()` không catch -> chương trình dừng

## 2.3 Ý nghĩa design

Điều này cho thấy:

* không phải lỗi nào cũng nên xử lý ngay tại nơi phát sinh
* nơi phát sinh lỗi chưa chắc là nơi phù hợp nhất để xử lý lỗi
* có nơi chỉ nên **phát hiện và ném**
* có nơi chỉ nên **dịch nghĩa lỗi**
* có nơi mới là **điểm xử lý cuối**

---

# 3. Exception hierarchy - phải hiểu như cây phân loại

## 3.1 Gốc là `Throwable`

Java gom tất cả thứ “có thể bị ném ra” dưới gốc `Throwable`.

Nó tách làm 2 nhánh lớn:

* `Error`
* `Exception`

## 3.2 `Error`

`Error` là lỗi nghiêm trọng ở mức JVM/hệ thống:

* `OutOfMemoryError`
* `StackOverflowError`

Bản chất:

* chương trình thường không recover an toàn được
* business code gần như không nên cố xử lý

Tư duy đúng:

* `Error` là dấu hiệu hệ thống đang hỏng nền tảng chạy

## 3.3 `Exception`

`Exception` là lỗi ở mức ứng dụng:

* input sai
* file không tồn tại
* DB lỗi
* logic sử dụng sai

Đây mới là vùng lập trình viên xử lý thường xuyên.

---

# 4. Checked vs Unchecked - bản chất thật sự

## 4.1 Checked exception là gì về bản chất

Checked exception là cách Java nói rằng:

“Lỗi này quan trọng đến mức compiler muốn ép caller phải biết tới nó.”

Tức là checked exception là **contract ở compile time**.

Ví dụ:

* `IOException`
* `SQLException`

Nếu method có thể ném checked exception:

* hoặc bắt
* hoặc khai báo `throws`

## 4.2 Unchecked exception là gì về bản chất

Unchecked exception là lỗi mà Java không ép compiler phải xử lý.
Thường là `RuntimeException` và class con.

Bản chất của nhóm này:

* đây là lỗi logic
* lỗi dùng sai API
* lỗi trạng thái không hợp lệ
* lỗi mà caller thường không có cách phục hồi hợp lý

Ví dụ:

* `NullPointerException`
* `IllegalArgumentException`
* `IllegalStateException`

## 4.3 Trade-off giữa checked và unchecked

### Checked - được gì

* ép người dùng API phải chú ý
* tốt với lỗi môi trường/tài nguyên
* thể hiện rõ method có risk gì

### Checked - mất gì

* code dài hơn
* `throws` lan khắp codebase
* nhiều chỗ catch chỉ để rethrow
* làm API nặng signature

### Unchecked - được gì

* code gọn hơn
* propagate tự nhiên
* phù hợp với service/business layer hiện đại
* dễ gom về global handler

### Unchecked - mất gì

* caller dễ quên mất risk
* nếu team yếu, dễ lạm dụng và ném lỗi không kiểm soát

## 4.4 Góc nhìn senior

Không phải hỏi “checked tốt hơn hay unchecked tốt hơn”, mà phải hỏi:

* lỗi này caller có recover được không?
* lỗi này là lỗi môi trường hay lỗi logic?
* có cần ép compile-time contract không?
* hệ thống của mình đang theo style nào?

---

# 5. `throw` và `throws` - bản chất từng cái

## 5.1 `throw`

`throw` là hành động **ném ra một object exception cụ thể**.

Nó làm 2 việc:

* tạo điểm đứt của luồng chạy hiện tại
* chuyển trạng thái hiện tại sang luồng lỗi

Ví dụ tư duy:

* validate input
* phát hiện business rule sai
* chủ động fail fast

## 5.2 `throws`

`throws` chỉ là **cam kết ở chữ ký method** rằng method này có thể phát ra loại lỗi nào đó.

Nó không ném gì cả.
Nó chỉ khai báo trách nhiệm.

## 5.3 Nhìn theo design

* `throw` = hành vi runtime
* `throws` = contract compile-time

---

# 6. `try-catch-finally` - bản chất là cơ chế chặn và phục hồi luồng lỗi

## 6.1 `try`

Nơi chứa đoạn code có rủi ro.

## 6.2 `catch`

Là điểm chặn exception để:

* xử lý
* chuyển nghĩa
* log
* fallback
* trả response phù hợp

## 6.3 `finally`

Là nơi cleanup tài nguyên/side effect bắt buộc phải làm sau cùng.

Ví dụ:

* đóng file
* release connection
* unlock resource
* clear context

## 6.4 Bản chất quan trọng nhất

`catch` không phải để “cho hết lỗi”.
`catch` là để **quyết định có đủ ngữ cảnh và quyền để xử lý lỗi hay chưa**.

Nếu chưa đủ:

* đừng catch bừa
* cứ để propagate lên

Đây là điểm rất quan trọng.

---

# 7. Memory / runtime nhìn thế nào khi exception xảy ra

## 7.1 Exception object được tạo ra

Khi `new IllegalArgumentException(...)`, một object được tạo trên heap như object bình thường.

## 7.2 Stack trace được gắn vào exception

Khi exception được tạo/ném, JVM capture stack trace:

* method nào gọi ai
* lỗi xảy ra ở đâu

Đó là lý do exception tương đối “đắt” hơn flow thường.

## 7.3 Vì sao không nên dùng exception cho flow bình thường

Vì nó tốn hơn:

* tạo object
* thu stack trace
* unwind stack

Cho nên:

* exception dành cho path bất thường
* không dùng làm logic phân nhánh thường xuyên

Ví dụ sai:

* lấy map key không có thì ném exception rồi dùng nó như case bình thường
* dùng exception để break vòng lặp

---

# 8. Stack trace - phải biết đọc như người debug thật

Khi nhìn stack trace, phải đọc theo thứ tự:

## 8.1 Dòng đầu

Cho biết:

* loại lỗi gì
* message là gì

Ví dụ:

* `java.lang.IllegalArgumentException: amount must be positive`

## 8.2 Dòng `at` đầu tiên

Đây thường là **điểm lỗi thực sự phát sinh**.

## 8.3 Các dòng tiếp theo

Là đường đi ngược của call stack:

* ai gọi ai
* lỗi đi lên bằng đường nào

## 8.4 Kỹ năng cần có

Khi debug:

* đừng đọc từ dưới lên trước
* đọc loại lỗi
* xác định dòng `at` đầu tiên của code mình
* rồi lần theo ngữ cảnh business

---

# 9. Custom Exception - bản chất là mô hình hóa lỗi nghiệp vụ

## 9.1 Vì sao phải tạo custom exception

Exception built-in của Java chỉ mô tả lỗi kỹ thuật chung chung.

Nhưng hệ thống thực tế có lỗi nghiệp vụ riêng:

* không đủ số dư
* tài khoản bị khóa
* không được giao dịch ngoài giờ
* mã chứng khoán không hợp lệ

Nếu chỉ ném `RuntimeException("error")`:

* người đọc không hiểu bản chất lỗi
* API khó map response
* log khó phân loại
* monitoring khó thống kê

## 9.2 Custom exception là domain language

Một custom exception tốt chính là ngôn ngữ của domain.

Ví dụ:

* `InsufficientBalanceException`
* `AccountLockedException`
* `PortfolioNotFoundException`

Nhìn tên là hiểu business đang fail ở đâu.

## 9.3 Kế thừa từ đâu

Thực tế enterprise hay chọn:

* extends `RuntimeException`

Vì:

* nhẹ hơn về dùng
* không làm phình `throws`
* dễ đẩy về global handler

---

# 10. Exception translation - rất quan trọng trong hệ thống nhiều layer

## 10.1 Translation là gì

Lỗi kỹ thuật ở tầng dưới có thể không phù hợp để đẩy nguyên xi lên tầng trên.

Ví dụ:

* repository bị `SQLException`
* service không nên expose `SQLException` ra controller
* service có thể đổi thành `DataAccessException` hoặc `BusinessException`

## 10.2 Vì sao cần translate

Vì mỗi layer có một ngôn ngữ riêng:

### Repository

ngôn ngữ kỹ thuật: SQL, DB, timeout, duplicate key

### Service

ngôn ngữ nghiệp vụ: account not found, order invalid

### Controller/API

ngôn ngữ giao tiếp client: errorCode, message, HTTP status

## 10.3 Đây là tư duy thiết kế tốt

Mỗi layer:

* không lộ chi tiết implementation quá sâu
* chỉ expose abstraction phù hợp

---

# 11. Root cause - tại sao phải giữ nguyên nhân gốc

## 11.1 Sai lầm phổ biến

Bắt exception rồi ném exception mới nhưng không truyền `cause`.

Hậu quả:

* mất stack trace gốc
* debug rất khó
* nhìn log không biết lỗi thật bắt đầu từ đâu

## 11.2 Tư duy đúng

Nếu bọc exception, hãy giữ nguyên cause.

Ví dụ tư duy:

* repository lỗi SQL
* service muốn đổi nghĩa lỗi
* nhưng vẫn giữ `cause` để sau này lần trace được nguồn gốc

## 11.3 Bản chất

Exception wrapping mà mất cause giống như:

* báo “có vấn đề”
* nhưng xóa toàn bộ bằng chứng hiện trường

---

# 12. Best practices - nhìn từ góc độ design

## 12.1 Fail fast

Phát hiện sai càng sớm càng tốt.

Ví dụ:

* input null
* amount âm
* trạng thái object sai

Lợi ích:

* lỗi gần nguồn
* dễ debug
* tránh truyền state bẩn xuống sâu

## 12.2 Không swallow exception

Swallow = catch rồi bỏ qua.

Đây là một trong những lỗi code nguy hiểm nhất:

* bug bị giấu
* dữ liệu có thể sai âm thầm
* hệ thống “trông có vẻ chạy” nhưng thực ra hỏng logic

## 12.3 Không catch quá rộng nếu chưa cần

`catch (Exception e)` chỉ nên dùng ở:

* boundary lớn
* global handler
* scheduler wrapper
* entry point cuối

Còn ở business code, nên bắt loại cụ thể hơn.

## 12.4 Không log lặp nhiều tầng

Một lỗi chỉ nên log ở nơi phù hợp nhất.

Nếu tầng nào cũng log:

* log rác
* khó đọc
* tưởng nhiều lỗi nhưng thực ra chỉ 1 lỗi

## 12.5 Message phải có nghĩa

Message tốt phải trả lời được:

* cái gì sai
* sai vì sao
* context gì

Không nên:

* `"error"`
* `"failed"`
* `"invalid"`

Nên kiểu:

* `"Amount must be greater than 0"`
* `"Account 12345 is locked"`
* `"Portfolio ABC not found"`

---

# 13. Business exception vs Technical exception

## 13.1 Technical exception

Là lỗi hạ tầng/kỹ thuật:

* DB connection fail
* file not found
* JSON parse fail
* network timeout

Đặc điểm:

* thường không phải do rule business
* cần log kỹ thuật
* thường không show raw detail cho user

## 13.2 Business exception

Là lỗi do rule nghiệp vụ:

* không đủ tiền
* user chưa KYC
* giao dịch ngoài giờ
* danh mục không hợp lệ

Đặc điểm:

* có thể show message dễ hiểu cho client
* thường map được sang mã lỗi rõ ràng
* nên có tên exception mang nghĩa domain

## 13.3 Tại sao phải tách

Vì:

* cách xử lý khác nhau
* cách log khác nhau
* mức độ ưu tiên alert khác nhau
* response cho frontend khác nhau

---

# 14. Theo góc nhìn layer trong ứng dụng

## 14.1 Repository layer

Chủ yếu gặp:

* `SQLException`
* timeout
* duplicate key
* data mapping lỗi

Vai trò:

* có thể ném lỗi kỹ thuật
* hoặc translate sang data-layer exception rõ hơn

## 14.2 Service layer

Đây là nơi quan trọng nhất về mặt exception design.

Nó nên:

* validate input
* ném business exception
* translate technical exception nếu cần
* không ôm quá nhiều `try-catch` vô ích

## 14.3 Controller layer

Thường không nên viết nhiều `try-catch` tay trong từng API.

Nên:

* để exception đi lên
* global exception handler xử lý thống nhất

## 14.4 Global exception handler

Đây là điểm:

* gom lỗi
* map sang HTTP status
* format response chuẩn
* gắn requestId / errorCode
* log có kiểm soát

Đây là nơi “kết thúc” exception ở ứng dụng web.

---

# 15. Try-with-resources - góc nhìn bản chất

## 15.1 Vì sao nó tồn tại

Lập trình I/O rất hay bị quên close resource:

* file
* stream
* DB connection
* statement
* result set

Nếu quên close:

* memory/resource leak
* hết connection pool
* file lock không giải phóng

## 15.2 Bản chất

`try-with-resources` là cú pháp giúp JVM tự gọi `close()` cho resource implement `AutoCloseable`.

## 15.3 Ý nghĩa thiết kế

Nó giảm:

* boilerplate
* quên cleanup
* bug tài nguyên

Nó là ví dụ tốt về:

* language feature giúp code an toàn hơn

---

# 16. Những hiểu nhầm phổ biến

## 16.1 “Catch hết để chương trình không lỗi”

Sai.
Catch không đúng chỗ chỉ làm giấu lỗi.

## 16.2 “Exception là xấu, nên tránh hoàn toàn”

Sai.
Exception là công cụ cần thiết.
Điều xấu là dùng sai.

## 16.3 “Cứ `throws Exception` cho nhanh”

Sai.
Làm API mơ hồ, mất ý nghĩa thiết kế.

## 16.4 “Chỉ cần message là đủ, không cần cause”

Sai.
Mất root cause là mất khả năng debug.

## 16.5 “Unchecked thì chuyên nghiệp hơn checked”

Không hẳn.
Phải theo bản chất lỗi và style hệ thống.

---

# 17. Ràng buộc và trade-off thực tế

## 17.1 Nếu dùng quá nhiều checked exception

Bạn sẽ gặp:

* method signature rất dài
* chain `throws` dày đặc
* caller khó đọc
* code nặng ceremony

## 17.2 Nếu lạm dụng unchecked exception

Bạn sẽ gặp:

* khó nhìn contract method
* lỗi propagate tùy tiện
* team junior dễ ném bừa

## 17.3 Nếu catch ở quá thấp

Bạn sẽ:

* thiếu context để xử lý
* log không đủ business meaning
* dễ nuốt lỗi

## 17.4 Nếu catch ở quá cao

Bạn sẽ:

* khó fallback cục bộ
* khó translate đúng nghĩa lỗi

Cho nên điểm hay là:

* phát hiện ở nơi gần lỗi
* translate ở nơi hiểu domain
* xử lý cuối ở boundary/global handler

---

# 18. Nên tự đặt câu hỏi 360 độ khi thấy một exception

Khi thiết kế hoặc đọc code, hãy tự hỏi:

1. Lỗi này là technical hay business?
2. Caller có recover được không?
3. Có cần checked không, hay unchecked là đủ?
4. Có nên translate exception ở layer này không?
5. Có cần custom exception không?
6. Message hiện tại đã đủ rõ chưa?
7. Có giữ root cause chưa?
8. Có đang catch quá rộng không?
9. Có log trùng nhiều tầng không?
10. Lỗi này nên map ra response gì cho client?

---

# 19. Kết quả cần đạt sau Day 4

Sau Day 4, bạn nên đạt được mức này:

## Mức 1 - cú pháp

* biết dùng `try-catch-finally`
* biết `throw`, `throws`
* phân biệt checked/unchecked

## Mức 2 - bản chất

* hiểu stack unwinding
* hiểu exception là object
* hiểu stack trace
* hiểu không dùng exception cho flow thường

## Mức 3 - design

* biết chỗ nào nên catch
* biết khi nào cần custom exception
* biết translate exception giữa các layer
* biết tách business và technical exception

## Mức 4 - thực chiến

* đọc log nhanh hơn
* debug lỗi có hệ thống hơn
* viết service/controller sạch hơn
* chuẩn bị tốt cho Spring Boot Global Exception Handler

---

# 20. Bài tập tự luyện để chốt Day 4

## Bài 1

Viết method validate transfer amount:

* null -> exception
* <= 0 -> exception

Mục tiêu:

* `IllegalArgumentException`
* fail fast

## Bài 2

Viết method parse tuổi từ string:

* số hợp lệ -> trả int
* sai format -> xử lý

Mục tiêu:

* `NumberFormatException`
* `try-catch`

## Bài 3

Viết logic đọc file text bằng `try-with-resources`

Mục tiêu:

* checked exception
* resource lifecycle

## Bài 4

Tạo `InsufficientBalanceException`

Mục tiêu:

* custom exception
* business meaning

## Bài 5

Giả lập repository ném `SQLException`, service bọc lại thành `DataAccessException`

Mục tiêu:

* exception translation
* preserve cause

## Bài 6

Tạo mock controller + global handler map:

* `BusinessException` -> 400
* `SystemException` -> 500

Mục tiêu:

* nhìn exception theo architecture thật

---

# 21. Chốt bản chất Day 4 trong 10 dòng

* Exception là cơ chế biểu diễn và truyền lỗi trong runtime.
* Nó tách luồng bình thường và luồng lỗi.
* Exception là object, có type, message, cause, stack trace.
* Checked = compiler ép xử lý; unchecked = runtime tự propagate.
* `throw` là ném lỗi, `throws` là khai báo khả năng ném lỗi.
* `catch` chỉ nên dùng khi thực sự biết xử lý gì.
* `finally` dùng cho cleanup; `try-with-resources` giúp đóng tài nguyên an toàn.
* Custom exception giúp mô hình hóa lỗi nghiệp vụ rõ ràng.
* Phải giữ root cause khi bọc exception.
* Thiết kế exception tốt giúp code dễ debug, dễ maintain, và rõ kiến trúc.

Nếu muốn, tôi sẽ viết tiếp cho bạn **Day 4 theo format học Java Core của bạn: Core components → memory operation →
constraints → trade-offs → design perspective → câu hỏi tự kiểm tra**.
