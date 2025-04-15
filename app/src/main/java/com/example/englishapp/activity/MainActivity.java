package com.example.englishapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.englishapp.R;
import com.example.englishapp.dao.UserDAO;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserDAO userDAO = new UserDAO();
        userDAO.getByID("vYUbgAInoovmmqVNkpJO",(user) -> {
            if(user != null){
                Log.w("USER_INFO",user.getEmail()+user.getPassword()+user.getUsername());
            }else{
                Log.w("USER_INFO","Error");
            }
        });
    }
}