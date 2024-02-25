package com.example.appliblog;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    // Instance de DatabaseHelper pour interagir avec la base de données SQLite
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialisation de DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Récupération des instances des éléments d'interface utilisateur
        final EditText etUsername = findViewById(R.id.etRegUsername);
        final EditText etPassword = findViewById(R.id.etRegPassword);
        final EditText etEmail = findViewById(R.id.etRegEmail);
        Button registerButton = findViewById(R.id.btnRegister);

        // Définition du comportement du bouton d'enregistrement
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String email = etEmail.getText().toString();

                // Validation de l'email et du mot de passe avant l'ajout dans la base de données
                if (isValidEmail(email) && isValidPassword(password)) {
                    addUserToDatabase(username, password, email);
                } else {
                    // Affichage de messages d'erreur en cas de validation échouée
                    if (!isValidEmail(email)) {
                        Toast.makeText(RegisterActivity.this, "L'email n'est pas valide.", Toast.LENGTH_SHORT).show();
                    }
                    if (!isValidPassword(password)) {
                        Toast.makeText(RegisterActivity.this, "Le mot de passe n'est pas valide.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    // Vérifie la validité de l'email avec une expression régulière
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    // Vérifie la validité du mot de passe avec une expression régulière
    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        return password.matches(passwordRegex);
    }

    // Ajoute un nouvel utilisateur dans la base de données
    private void addUserToDatabase(String username, String password, String email) {
        // Obtention d'une instance de la base de données en écriture
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Création d'un ContentValues pour organiser les données à insérer
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("email", email);

        // Insertion des données dans la base de données
        long newRowId = db.insert("users", null, values);

        // Confirmation de l'enregistrement à l'utilisateur
        if (newRowId != -1) {
            Toast.makeText(this, "Utilisateur enregistré avec succès!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Erreur d'enregistrement de l'utilisateur", Toast.LENGTH_SHORT).show();
        }
    }
}
