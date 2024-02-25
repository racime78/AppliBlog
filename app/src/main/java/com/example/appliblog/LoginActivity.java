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

public class LoginActivity extends AppCompatActivity {

    // Déclaration des variables pour les éléments d'interface utilisateur
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private DatabaseHelper dbHelper; // Instance pour interagir avec la base de données

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

        // Initialisation de dbHelper et des éléments d'interface utilisateur
        dbHelper = new DatabaseHelper(this); // Création de l'instance dbHelper pour accéder à la base de données
        etUsername = findViewById(R.id.etUsername); // Champ de saisie pour le nom d'utilisateur
        etPassword = findViewById(R.id.etPassword); // Champ de saisie pour le mot de passe
        btnLogin = findViewById(R.id.btnLogin); // Bouton de connexion

        // Configuration de l'écouteur d'événements pour le bouton de connexion
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupération des saisies de l'utilisateur
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                // Vérification des identifiants de l'utilisateur avec la base de données
                if(dbHelper.checkUser(username, password)) {
                    // Si les identifiants sont corrects, enregistre le nom d'utilisateur actuel
                    saveCurrentUser(username);

                    Toast.makeText(LoginActivity.this, "Connexion réussie", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();  // Ferme LoginActivity
                } else {
                    Toast.makeText(LoginActivity.this, "Échec de la connexion", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Configuration de l'écouteur d'événements pour le texte d'inscription
        TextView tvRegister = findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigue vers RegisterActivity lorsque l'utilisateur clique sur le texte d'inscription
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}