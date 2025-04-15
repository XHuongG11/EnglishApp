package com.example.englishapp.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.englishapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {

    protected BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }
}

//        bottomNavigationView = findViewById(R.id.bottom_navigation);
//
//        // Set trạng thái của menu theo activity hiện tại
//        updateNavigationBarState();
//
//        bottomNavigationView.setOnItemSelectedListener(item -> {
//            int id = item.getItemId();
//            Class<?> targetActivity = null;
//            Intent intent = null;
//
//            if (id == R.id.nav_home && !(this instanceof HomeActivity)) {
//                targetActivity = HomeActivity.class;
////            } else if (id == R.id.nav_favourite && !(this instanceof FavoriteActivity)) {
////                targetActivity = FavoriteActivity.class;
////                intent = new Intent(this, targetActivity);
////                // Kiểm tra nếu đang ở Home thì truyền nameUser
////                if (this instanceof Home) {
////                    String idUser = ((Home) this).idUser; // Giả sử Home có method getNameUser()
////                    intent.putExtra("ID_USER", idUser);
////                }
////            } else if (id == R.id.nav_cart && !(this instanceof CartActivity)) {
////                targetActivity = CartActivity.class;
////                intent = new Intent(this, targetActivity);
////                // Kiểm tra nếu đang ở Home thì truyền nameUser
////                if (this instanceof Home) {
////                    String idUser = ((Home) this).idUser; // Giả sử Home có method getNameUser()
////                    intent.putExtra("idUser", idUser);
////                }
////            } else if (id == R.id.nav_profile && !(this instanceof ProfileActivity)) {
////                targetActivity = ProfileActivity.class;
////                intent = new Intent(this, targetActivity);
////                if (this instanceof Home) {
////                    String nameUser = ((Home) this).nameUser; // Giả sử Home có method getNameUser()
////                    intent.putExtra("nameUser", nameUser);
////                }
//            }
//
//            if (targetActivity != null) {
//                if (intent == null) {
//                    intent = new Intent(this, targetActivity);
//                }
//                startActivity(intent);
//                overridePendingTransition(0, 0);
//                return true;
//            }
//            return false;
//        });
//    }
//
//    private void updateNavigationBarState() {
//        if (bottomNavigationView != null) {
//            int id = getNavigationMenuItemId();
//            bottomNavigationView.setSelectedItemId(id);
//        }
//    }
//
//    protected int getNavigationMenuItemId() {
//        return R.id.nav_home; // Mặc định là Home
//    }