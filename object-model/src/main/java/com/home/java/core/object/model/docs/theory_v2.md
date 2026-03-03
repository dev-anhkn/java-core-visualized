1. Object vs Reference
   • Object: Một object là một thể hiện của lớp (class). Nó chứa dữ liệu (fields) và hành vi (methods). Mỗi object được
   cấp phát bộ nhớ trong heap.
   • Reference: Một reference là một biến dùng để trỏ đến một object trong bộ nhớ. Khi bạn khai báo một biến object, bạn
   đang tạo một reference trỏ đến một đối tượng trên heap. Reference chỉ chứa địa chỉ của object, chứ không phải dữ liệu
   của nó.

Ví dụ:

MyClass obj1 = new MyClass(); // obj1 là reference, đối tượng MyClass được tạo trong heap

Điểm quan trọng:
• Khi bạn gán một reference cho một reference khác, bạn không sao chép object, mà chỉ sao chép địa chỉ bộ nhớ (tức là cả
hai reference sẽ trỏ đến cùng một object).
• Pass-by-Reference: Khi bạn truyền một reference vào phương thức, bạn truyền địa chỉ của object, không phải object thực
tế.

2. Heap vs Stack
   • Stack: Là khu vực bộ nhớ được quản lý tự động. Khi bạn khai báo một biến primitive hoặc reference, nó được lưu trữ
   trong stack. Stack có tính năng tự động giải phóng bộ nhớ khi các phương thức hoàn tất. Stack quản lý bộ nhớ theo
   dạng LIFO (Last In, First Out).
   • Ưu điểm: Quản lý bộ nhớ nhanh, vì không cần phải thực hiện việc phân bổ hay giải phóng thủ công.
   • Nhược điểm: Không thể lưu trữ đối tượng có kích thước lớn hoặc có thời gian sống dài.
   • Heap: Là khu vực bộ nhớ được quản lý thủ công hoặc bởi Garbage Collector. Các đối tượng trong Java (và hầu hết các
   ngôn ngữ OOP) được tạo và quản lý trong heap. Bộ nhớ trong heap cần được phân bổ và giải phóng, thường thông qua
   Garbage Collection (GC).
   • Ưu điểm: Có thể lưu trữ các đối tượng có kích thước lớn và có thời gian sống dài.
   • Nhược điểm: Quản lý bộ nhớ chậm hơn stack vì phải thực hiện Garbage Collection.

Điểm quan trọng:
• Stack nhanh hơn vì bộ nhớ được cấp phát và giải phóng tự động, nhưng dung lượng giới hạn.
• Heap lớn hơn và linh hoạt hơn, nhưng cần phải quản lý bộ nhớ, làm chậm quá trình.

3. Final Keyword
   • Final variable: Khi một biến được khai báo với từ khóa final, nó không thể thay đổi giá trị sau khi được khởi tạo.
   Đối với primitive, giá trị không thay đổi, còn đối với reference, địa chỉ của object không thể thay đổi, nhưng nội
   dung của object vẫn có thể thay đổi.
   • Final method: Không thể override method này trong lớp con.
   • Final class: Không thể kế thừa từ lớp này.

Ví dụ:

final int x = 10; // Không thể gán lại giá trị cho x

Điểm quan trọng:
• Immutability: Lớp có thể được khai báo là final để không bị kế thừa và thay đổi. Điều này giúp đảm bảo tính toàn vẹn
của dữ liệu trong concurrency (đa luồng).

4. Immutable Objects và Benefits trong Concurrency
   • Immutable Objects: Một object immutable là object mà không thể thay đổi sau khi nó được tạo. Khi bạn tạo một đối
   tượng immutable, bạn không thể thay đổi giá trị của các thuộc tính của nó sau khi đối tượng đã được khởi tạo. Các lớp
   String trong Java là ví dụ điển hình.

Điểm quan trọng:
• Thread-Safety: Vì immutable object không thể thay đổi sau khi được tạo, chúng rất hữu ích trong môi trường
concurrency, nơi mà nhiều thread có thể cùng truy cập vào dữ liệu mà không lo xảy ra xung đột hay thay đổi không mong
muốn.
• Sử dụng Final Keyword: Các thuộc tính của immutable object thường được khai báo là final để đảm bảo chúng không thể
thay đổi sau khi đối tượng đã được khởi tạo.
• Tối ưu hóa Garbage Collection: Vì immutable objects không thay đổi, hệ thống có thể tối ưu hóa việc thu gom rác (
Garbage Collection) bằng cách chia sẻ đối tượng giữa các thread thay vì tạo bản sao mới.

Ví dụ:

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

Lợi ích trong Concurrency:
• Không cần đồng bộ hóa: Các object immutable có thể được chia sẻ giữa các thread mà không cần phải đồng bộ hóa, vì
không thể thay đổi trạng thái của chúng.
• Giảm độ phức tạp và sai sót: Không phải lo lắng về vấn đề race conditions khi các thread cùng sửa đổi cùng một object,
vì immutable object không thể bị thay đổi.

Kết luận:

Để đạt được level senior, bạn cần nắm vững:
• Cách các object và reference hoạt động.
• Quản lý bộ nhớ trong stack và heap.
• Sử dụng từ khóa final để bảo vệ giá trị và tránh sự thay đổi không mong muốn.
• Hiểu và áp dụng các immutable objects trong lập trình đồng thời (concurrency), giúp tránh các lỗi liên quan đến đồng
bộ hóa và tối ưu hóa hiệu suất.

Việc hiểu và áp dụng những kiến thức này sẽ giúp bạn phát triển ứng dụng hiệu quả và an toàn trong môi trường đa luồng.