package com.example.englishapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.englishapp.R;

public class SignUpActivity extends AppCompatActivity {
    private EditText fullNameEditText, usernameEditText, passwordEditText, confirmPasswordEditText;
    private LinearLayout signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Ánh xạ các thành phần
        fullNameEditText = findViewById(R.id.fullnameSignUp);
        usernameEditText = findViewById(R.id.usernameSignUp);
        passwordEditText = findViewById(R.id.passwordSignUp);
        confirmPasswordEditText = findViewById(R.id.confirmpasswordSignUp);
        signUpButton = findViewById(R.id.signUpButton);

        // Xử lý sự kiện nút "Sign up"
        signUpButton.setOnClickListener(v -> {
            // Lấy dữ liệu từ các trường nhập liệu
            String fullName = fullNameEditText.getText().toString();
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();

            // Thêm logic đăng ký ở đây (ví dụ: kiểm tra password và confirmPassword khớp nhau)
            if (password.equals(confirmPassword)) {
                // Đăng ký thành công, có thể đóng dialog
                finish();
            } else {
                // Hiển thị thông báo lỗi (có thể dùng Toast hoặc Snackbar)
            }
        });

        // Đóng dialog khi nhấn ngoài
        setFinishOnTouchOutside(true);
    }
}