package com.example.asm.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.asm.LoginData;
import com.example.asm.LoginResponse;
import com.example.asm.LoginService;
import com.example.asm.MainActivity;
import com.example.asm.MainActivity2;
import com.example.asm.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    // Khai báo các view và Retrofit service
    private EditText textInputUsername;
    private EditText textInputPassword;
    private Button buttonLogin;
    private Button buttonDngkys;
    private ProgressBar progressBar;
    private LoginService loginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ánh xạ các view
        textInputUsername = findViewById(R.id.editTextUsername);
        textInputPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonDngkys = findViewById(R.id.buttonDangKys);
        progressBar = findViewById(R.id.progressBar);

        buttonDngkys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        // Khởi tạo Retrofit và tạo instance của service
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.9.102:8000/") // Thay thế bằng địa chỉ backend của bạn
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loginService = retrofit.create(LoginService.class);

        buttonLogin.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {
        String username = textInputUsername.getText().toString();
        String password = textInputPassword.getText().toString();

        // Kiểm tra xem username và password có hợp lệ không
        if (!username.isEmpty() && !password.isEmpty()) {
            // Gửi yêu cầu đăng nhập thông qua Retrofit
            Call<LoginResponse> call = loginService.loginUser(new LoginData(username, password));
            call.enqueue(new Callback<LoginResponse>() {

                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        LoginResponse loginResponse = response.body();
                        String message = loginResponse.getMessage();

                        // Kiểm tra message không null trước khi sử dụng
                        if (message != null) {
                            if (username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")) {
                                // Nếu message là "admin", chuyển sang MainActivity
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            } else {
                                // Nếu không phải "admin", chuyển sang MainActivity2
                                startActivity(new Intent(LoginActivity.this, MainActivity2.class));
                            }
                        } else {
                            // Xử lý khi message là null
                            // Ví dụ: Hiển thị thông báo hoặc xử lý tương ứng
                            Toast.makeText(LoginActivity.this, "Message is null", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Xử lý khi response không thành công hoặc response body là null
                        Toast.makeText(LoginActivity.this, "Response is not successful", Toast.LENGTH_SHORT).show();
                    }
                }



                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    // Xử lý khi gặp lỗi
                    Toast.makeText(LoginActivity.this, "Lỗi khi gửi yêu cầu đến server", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        }
    }
}
