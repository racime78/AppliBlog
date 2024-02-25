package com.example.appliblog;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ImageView ivArticle1;
    TextView tvArticle1Title;
    ImageView ivArticle2;
    TextView tvArticle2Title;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivArticle1 = findViewById(R.id.ivArticle1);
        tvArticle1Title = findViewById(R.id.tvArticle1Title);
        ivArticle2 = findViewById(R.id.ivArticle2);
        tvArticle2Title = findViewById(R.id.tvArticle2Title);

        ivArticle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Article1.class);
                startActivity(intent);
            }
        });

        ivArticle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Article2.class);
                startActivity(intent);
            }
        });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "La récupération du token d'inscription FCM a échoué", task.getException());
                            return;
                        }

                        String token = task.getResult();
                        Log.d(TAG, "Token FCM : " + token);
                        Toast.makeText(MainActivity.this, "Token FCM : " + token, Toast.LENGTH_SHORT).show();
                    }
                });

        demanderPermissionNotification();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent homeIntent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(homeIntent);
                    return true;
                case R.id.navigation_specific:
                    Intent accountIntent = new Intent(MainActivity.this, AccountActivity.class);
                    startActivity(accountIntent);
                    return true;
            }
            return false;
        });
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission accordée
                } else {
                    Toast.makeText(MainActivity.this, "Permission de notification refusée", Toast.LENGTH_SHORT).show();
                }
            });

    private void demanderPermissionNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                // Permission déjà accordée
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // Expliquer la nécessité de la permission ici
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);
        return true;
    }
}