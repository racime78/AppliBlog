package com.example.appliblog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.messaging.FirebaseMessaging;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView articlesRecyclerView;
    private ArticleAdapter articleAdapter;
    private List<Article> articlesList = new ArrayList<>();
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        articlesRecyclerView = findViewById(R.id.articlesRecyclerView);
        articlesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialisez l'adapter avec une liste vide dès le départ
        articleAdapter = new ArticleAdapter(this, articlesList);
        articlesRecyclerView.setAdapter(articleAdapter);

        // Chargez les articles de la base de données
        loadArticlesFromDatabase();

        // Initialisation et configuration de Firebase Messaging pour les notifications
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Récupérer le token d'inscription FCM et le loguer
                        String token = task.getResult();
                        Log.d(TAG, "FCM Registration Token: " + token);
                        Toast.makeText(MainActivity.this, "FCM Token: " + token, Toast.LENGTH_SHORT).show();
                    }
                });

        // Configuration de la BottomNavigationView
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    // Intent pour revenir à l'accueil, si nécessaire
                    return true;
                case R.id.navigation_specific:
                    // Intent pour aller à l'activité du compte utilisateur
                    Intent accountIntent = new Intent(MainActivity.this, AccountActivity.class);
                    startActivity(accountIntent);
                    return true;
            }
            return false;
        });

        FloatingActionButton fab = findViewById(R.id.fab_add_article);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Création de l'intent pour démarrer AddArticleActivity
                Intent intent = new Intent(MainActivity.this, AddArticleActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadArticlesFromDatabase() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Cursor cursor = dbHelper.getAllArticles();
        List<Article> newArticlesList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ARTICLE_ID));
                String title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TITLE));
                String imageUrl = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE_URL));
                String content = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CONTENT));

                Article article = new Article(id, title, imageUrl, content);
                newArticlesList.add(article);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // Mettez à jour la liste dans l'adapter avec les nouveaux articles
        articlesList.clear();
        articlesList.addAll(newArticlesList);

        // Notifiez l'adapter que les données ont changé pour rafraîchir l'affichage
        articleAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Cela garantit que la liste des articles est rechargée chaque fois que MainActivity revient au premier plan.
        loadArticlesFromDatabase();
    }}
