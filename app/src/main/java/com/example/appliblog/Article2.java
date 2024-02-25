package com.example.appliblog;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Article2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article2);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent homeIntent = new Intent(Article2.this, MainActivity.class);
                    startActivity(homeIntent);
                    return true;
                case R.id.navigation_specific:
                    Intent accountIntent = new Intent(Article2.this, AccountActivity.class);
                    startActivity(accountIntent);
                    return true;
            }
            return false;
        });
    }
}
