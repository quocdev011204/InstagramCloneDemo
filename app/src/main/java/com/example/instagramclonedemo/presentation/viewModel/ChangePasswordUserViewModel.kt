package com.example.instagramclonedemo.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class ChangePasswordUserViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    var oldPassword: String = ""
    var newPassword: String = ""
    var confirmPassword: String = ""


    fun changePassword(onResult: (Boolean, String) -> Unit) {
        // lambda function với hai tham số
        // Boolean : thành công hay thất bại
        // String : thông báo được trả về
        viewModelScope.launch {
            // Khởi tạo coroutine  trong phạm vi ViewModel để đảm bảo thao tác bất đồng bộ được thực hiện mà không bị huỷ
            val user = auth.currentUser
            if (user != null && oldPassword.isNotEmpty() && newPassword.isNotEmpty() && newPassword == confirmPassword) {
                val email = user.email
                if (email != null) {
                    val credential = EmailAuthProvider.getCredential(email, oldPassword)
                    // Tạo đối tượng AuthCredential sử dụng email và mật khẩu cũ (oldPassword).
                    //Thực hiện xác thực lại người dùng với đối tượng credential.
                    user.reauthenticate(credential).addOnCompleteListener { reAuthTask ->
                        if (reAuthTask.isSuccessful) {
                            user.updatePassword(newPassword).addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    onResult(true, "Đổi mật khẩu thành công")
                                } else {
                                    onResult(false, "Đổi mật khẩu thất bại: ${updateTask.exception?.message}")
                                }
                            }
                        } else {
                            onResult(false, "Mật khẩu hiện tại không đúng: ${reAuthTask.exception?.message}")
                        }
                    }
                } else {
                    onResult(false, "Email của người dùng không tồn tại")
                }
            } else {
                if (newPassword != confirmPassword) {
                    onResult(false, "Mật khẩu mới không trùng khớp")
                } else {
                    onResult(false, "Các trường không được để trống")
                }
            }
        }
    }
}