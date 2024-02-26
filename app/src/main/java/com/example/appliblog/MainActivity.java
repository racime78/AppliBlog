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
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Activité principale qui affiche une liste d'articles et gère les notifications via Firebase Messaging.
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView articlesRecyclerView;
    private ArticleAdapter articleAdapter;
    private List<Article> articlesList = new ArrayList<>();
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation du RecyclerView et de son adapter
        articlesRecyclerView = findViewById(R.id.articlesRecyclerView);
        articlesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        articleAdapter = new ArticleAdapter(this, articlesList);
        articlesRecyclerView.setAdapter(articleAdapter);

        // Chargement des articles depuis la base de données
        loadArticlesFromDatabase();

        // Configuration de Firebase Messaging
        setupFirebaseMessaging();

        // Configuration de la navigation inférieure
        setupBottomNavigationView();

        // Configuration du FloatingActionButton pour ajouter des articles
        setupFloatingActionButton();
    }

    /**
     * Charge les articles depuis la base de données et met à jour le RecyclerView.
     */
    private void loadArticlesFromDatabase() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Cursor cursor = dbHelper.getAllArticles();

        articlesList.clear(); // Efface la liste actuelle pour les nouveaux articles

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ARTICLE_ID));
            String title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TITLE));
            String imageUrl = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE_URL));
            String content = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CONTENT));
            articlesList.add(new Article(id, title, imageUrl, content));
        }
        cursor.close();
        articleAdapter.notifyDataSetChanged(); // Notifie l'adapter du changement de données
    }

    /**
     * Initialise et configure Firebase Messaging.
     */
    private void setupFirebaseMessaging() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    // Récupère le token d'inscription FCM et le logue
                    String token = task.getResult();
                    Log.d(TAG, "FCM Registration Token: " + token);
                });
    }

    /**
     * Configure la BottomNavigationView avec des écouteurs pour la navigation.
     */
    private void setupBottomNavigationView() {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_specific) {
                startActivity(new Intent(MainActivity.this, AccountActivity.class));
            }
            return true;
        });
    }

    /**
     * Configure le FloatingActionButton pour démarrer AddArticleActivity.
     */
    private void setupFloatingActionButton() {
        FloatingActionButton fab = findViewById(R.id.fab_add_article);
        fab.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, AddArticleActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recharge la liste des articles à chaque retour sur l'activité
        loadArticlesFromDatabase();
    }
}
