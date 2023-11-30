package com.example.asm;

public class RegistrationData {

    private String username;
    private String password;

    public RegistrationData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and setters (hoặc có thể sử dụng các phương thức khác để truy cập dữ liệu nếu cần)

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
