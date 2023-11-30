package com.example.asm;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegistrationService {

    @POST("/register") // Địa chỉ endpoint xử lý đăng ký trên backend
    Call<Void> registerUser(@Body RegistrationData registrationData);
}
