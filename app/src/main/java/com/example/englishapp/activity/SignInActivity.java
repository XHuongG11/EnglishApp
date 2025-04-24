package com.example.englishapp.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.englishapp.R;
import com.example.englishapp.service.LoginService;

public class SignInActivity extends AppCompatActivity {
    private EditText usernameEditText, passwordEditText;
    private TextView forgotPasswordText;
    private LinearLayout loginButton, btnSignUp;
    View blurBackground;
    View layoutSignIn;
    View layoutSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auth);

        layoutSignIn = findViewById(R.id.layoutSignIn);
        layoutSignUp = findViewById(R.id.layoutSignUp);
        blurBackground = findViewById(R.id.blurBackground);

        btnSignUp = layoutSignIn.findViewById(R.id.signUpLayout); // nút ở layout signin
//        Button btnBack = layoutSignUp.findViewById(R.id.btnBack);     // mũi tên hoặc nút quay về trong signup

        // Khi bấm nút Sign Up
        btnSignUp.setOnClickListener(v -> {
            showSignUpLayout();
        });

        blurBackground.setOnClickListener(v -> {
            hideSignUpLayout();
        });
        // Ánh xạ các thành phần
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        forgotPasswordText = findViewById(R.id.forgotPasswordText);
        loginButton = layoutSignIn.findViewById(R.id.loginButton);
       //signUpButton = findViewById(R.id.signUpButton);

        // Xử lý sự kiện nút "Login"
        loginButton.setOnClickListener(v -> {
            String email = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            LoadingDialog loadingDialog = new LoadingDialog(this);
            loadingDialog.show(); // Hiện dialog trước khi login
            LoginService.login(email, password, (success, message) -> {
                loadingDialog.dismiss(); // Tắt dialog sau khi có phản hồi
                if (success) {
                    Toast.makeText(SignInActivity.this, message, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    showCustomErrorDialog();
                }
            });
        });
        // Xử lý sự kiện nút "Forgot Password?"
        forgotPasswordText.setOnClickListener(v -> {
            // Chuyển đến màn hình quên mật khẩu (nếu có)
        });
    }
    private void showSignUpLayout() {
        layoutSignUp.setVisibility(View.VISIBLE);
        blurBackground.setVisibility(View.VISIBLE);
        blurBackground.setAlpha(0f);
        blurBackground.animate().alpha(1f).setDuration(300).start();

        layoutSignUp.setTranslationY(layoutSignUp.getHeight()); // đảm bảo trượt từ dưới
        layoutSignUp.animate()
                .translationY(0)
                .setDuration(500)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }

    private void hideSignUpLayout() {
        blurBackground.animate().alpha(0f).setDuration(300).withEndAction(() -> {
            blurBackground.setVisibility(View.GONE);
        }).start();

        layoutSignUp.animate()
                .translationY(layoutSignUp.getHeight())
                .setDuration(500)
                .withEndAction(() -> {
                    layoutSignUp.setVisibility(View.GONE);
                }).start();
    }
    private void showCustomErrorDialog(){
        View dialogView = getLayoutInflater().inflate(R.layout.custom_alert_dialog, null);

        AlertDialog dialog = new AlertDialog.Builder(SignInActivity.this)
                .setView(dialogView)
                .create();

        // Cho dialog trong suốt nền xung quanh
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextView title = dialogView.findViewById(R.id.dialogTitle);
        TextView messageText = dialogView.findViewById(R.id.dialogMessage);
        LinearLayout button = dialogView.findViewById(R.id.dialogButton);

        title.setText("Đăng nhập thất bại");
        messageText.setText("Tài khoản hoặc mật khẩu không đúng. Vui lòng thử lại.");

        button.setOnClickListener(v1 -> dialog.dismiss());

        dialog.show();
        dialog.getWindow().setLayout(
                (int) getResources().getDisplayMetrics().density * 380, // width = 300dp
                WindowManager.LayoutParams.WRAP_CONTENT
        );
    }
//    Dialog loadingDialog;
//    private void showLoadingDialog() {
//        View view = getLayoutInflater().inflate(R.layout.loading_dialog, null);
//        loadingDialog = new Dialog(SignInActivity.this);
//        loadingDialog.setContentView(view);
//        loadingDialog.setCancelable(false); // Không cho bấm ra ngoài để tắt
//        if (loadingDialog.getWindow() != null) {
//            loadingDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        }
//        loadingDialog.show();
//    }
//
//    private void hideLoadingDialog() {
//        if (loadingDialog != null && loadingDialog.isShowing()) {
//            loadingDialog.dismiss();
//        }
//    }

}