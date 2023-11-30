package com.example.asm;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {
    @POST("/login") // Địa chỉ endpoint xử lý đăng nhập trên backend
    Call<LoginResponse> loginUser(@Body LoginData loginData);
}
