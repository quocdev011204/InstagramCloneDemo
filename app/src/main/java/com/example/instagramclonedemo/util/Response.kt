package com.example.instagramclonedemo.util

// khai báo của sealed class Response với một tham số kiểu T
// out trước kiểu T cho biết T là một kiểu contravariant, cho phép lớp con của Response có kiểu con của T.
sealed class Response<out T>{
    object Loading: Response<Nothing>()
    // object Loading: Response<Nothing>(): Đây là một đối tượng (object) con trong sealed class Response. Nó được gọi là Loading và là một đối tượng duy nhất (singleton). Kiểu của Loading là Response<Nothing>, trong đó Nothing là một kiểu không có giá trị, chỉ để biểu thị rằng Loading không chứa dữ liệu.

    //data class Success<out T>(val data: T): Response<T>(): Đây là một lớp dữ liệu (data class) con trong sealed class Response. Nó được gọi là Success và chứa một trường dữ liệu data có kiểu T. Kiểu của Success là Response<T>, trong đó T được thừa kế từ sealed class cha.
    data class Success<out T>(
        val data: T
    ): Response<T>()

    //data class Error(val message: String): Response<Nothing>(): Đây cũng là một lớp dữ liệu (data class) con trong sealed class Response. Nó được gọi là Error và chứa một trường dữ liệu message có kiểu String. Kiểu của Error là Response<Nothing>, chỉ để biểu thị rằng Error không chứa dữ liệu ngoài trường message.
    data class Error(
        val message : String
    ): Response<Nothing>()
}

/**
 * Tổng quan, sealed class Response định nghĩa các lớp con để biểu thị các trạng thái khác nhau của một phản hồi (response),
 * chẳng hạn như "Loading", "Success" và "Error".
 * Lớp con Loading không chứa dữ liệu,
 * Trong khi Success chứa dữ liệu được đại diện bởi trường data và Error chứa thông báo lỗi trong trường message.
 * Việc sử dụng sealed class giúp rõ ràng và dễ dàng xử lý các trạng thái phản hồi khác nhau trong mã của bạn.
 */
