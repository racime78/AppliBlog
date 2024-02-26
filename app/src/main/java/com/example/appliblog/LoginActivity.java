package com.example.appliblog;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

/**
 * Activité permettant à l'utilisateur de se connecter à l'application.
 * Offre également un lien vers l'activité d'inscription pour les nouveaux utilisateurs.
 */
public class LoginActivity extends AppCompatActivity {

    // Éléments d'interface utilisateur
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private DatabaseHelper dbHelper; // Utilisé pour accéder à la base de données

    /**
     * Enregistre le nom d'utilisateur actuel dans les préférences partagées pour maintenir la session.
     * @param username Le nom d'utilisateur à enregistrer.
     */
    private void saveCurrentUser(String username) {
        SharedPreferences sharedPreferences = getSharedPreferences("userSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialisation de dbHelper et des composants d'interface utilisateur
        dbHelper = new DatabaseHelper(this);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // Gestion de l'événement click sur le bouton de connexion
        btnLogin.setOnClickListener(v -> loginUser());

        // Lien vers l'inscription pour les nouveaux utilisateurs
        TextView tvRegister = findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Gère le processus de connexion en vérifiant les identifiants avec la base de données.
     */
    private void loginUser() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (dbHelper.checkUser(username, password)) {
            saveCurrentUser(username); // Sauvegarde de l'utilisateur courant pour la session
            Toast.makeText(this, "Connexion réussie", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish(); // Ferme LoginActivity après une connexion réussie
        } else {
            Toast.makeText(this, "Échec de la connexion", Toast.LENGTH_SHORT).show();
        }
    }
}
