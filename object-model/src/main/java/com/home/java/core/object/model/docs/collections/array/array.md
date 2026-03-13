# Array – tóm tắt những gì cần nắm

## 1. Array là gì?

Array là cấu trúc dữ liệu lưu nhiều phần tử **cùng kiểu** trong **1 vùng dữ liệu có thứ tự theo index**.

``` java
int[] arr = new int[5];
````
---

## 2. Bản chất cốt lõi

* Kích thước **cố định** ngay khi tạo
* Truy cập theo `index` rất nhanh: `O(1)`
* Phần tử được tổ chức liền nhau theo chỉ số
* Với `primitive array` → lưu giá trị
* Với `object array` → lưu reference

---

## 3. Ưu điểm

* Lấy phần tử theo index rất nhanh
* Đơn giản, nhẹ
* Phù hợp khi biết trước số lượng phần tử

---

## 4. Nhược điểm

* Không tự giãn ra được
* Chèn/xóa giữa mảng tốn `O(n)`
* Ít linh hoạt hơn `ArrayList`

---

## 5. Những thứ phải nhớ

* Index bắt đầu từ `0`
* Phần tử cuối là: `arr[arr.length - 1]`
* Array dùng **`length`**, không phải `size()` hay `length()`
* Truy cập sai index sẽ lỗi `ArrayIndexOutOfBoundsException`

---

## 6. So với ArrayList

* `Array` → size cố định
* `ArrayList` → bên trong dùng array nhưng có thể tự mở rộng

---

## 7. Câu chốt để nhớ

> Array là cấu trúc dữ liệu lưu các phần tử cùng kiểu, truy cập theo index rất nhanh, nhưng kích thước cố định và kém
> linh hoạt khi thêm/xóa.

