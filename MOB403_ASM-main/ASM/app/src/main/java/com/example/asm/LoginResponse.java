package com.example.asm;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("message") // Thay đổi tên này nếu field trong JSON response có tên khác
    private String message;
    private String username;
    private String password;

    // Các getter và setter cho các trường dữ liệu cần lấy từ response
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
