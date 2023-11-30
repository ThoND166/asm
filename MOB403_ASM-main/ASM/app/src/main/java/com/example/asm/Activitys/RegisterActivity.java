package com.example.asm.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.asm.R;
import com.example.asm.RegistrationData;
import com.example.asm.RegistrationService;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText textInputUsername;
    private TextInputEditText textInputPassword;
    private Button buttonRegister;
    private RegistrationService registrationService;
    private TextView tvdangnhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textInputUsername = findViewById(R.id.textInputUsername);
        textInputPassword = findViewById(R.id.textInputPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        tvdangnhap = findViewById(R.id.tvDangNhap);

        tvdangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.9.102:8000/") // Thay thế bằng địa chỉ backend của bạn
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        registrationService = retrofit.create(RegistrationService.class);

        buttonRegister.setOnClickListener(v -> handleRegistration());
    }

    private void handleRegistration() {
        String username = textInputUsername.getText().toString();
        String password = textInputPassword.getText().toString();

        RegistrationData registrationData = new RegistrationData(username, password);

        Call<Void> call = registrationService.registerUser(registrationData);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                } else {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                    // Xử lý thất bại, có thể cung cấp thông báo hoặc yêu cầu người dùng thử lại
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Lỗi khi gửi yêu cầu đến server!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
