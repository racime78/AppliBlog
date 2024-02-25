package com.example.appliblog;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AccountActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText etNewUsername;
    private EditText etNewPassword;
    private Button btnSaveChanges;

    private void saveCurrentUser(String username) {
        SharedPreferences sharedPreferences = getSharedPreferences("userSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.apply();
    }


    public String getCurrentUsername() {
        SharedPreferences sharedPreferences = getSharedPreferences("userSession", MODE_PRIVATE);
        return sharedPreferences.getString("username", null); // Retourne `null` si "username" n'existe pas
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        dbHelper = new DatabaseHelper(this);

        // Initialisation des EditTexts et du Button pour les mises à jour
        etNewUsername = findViewById(R.id.etNewUsername);
        etNewPassword = findViewById(R.id.etNewPassword);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        // Initialisation des TextViews pour afficher les infos actuelles
        TextView tvCurrentUsername = findViewById(R.id.tvCurrentUsername);
        TextView tvCurrentEmail = findViewById(R.id.tvCurrentEmail);

        // Récupérer le nom d'utilisateur actuel et l'afficher
        String currentUsername = getCurrentUsername();
        if (currentUsername != null) {
            tvCurrentUsername.setText(String.format("Nom d'utilisateur actuel: %s", currentUsername));

            // Récupérer les détails de l'utilisateur depuis la base de données pour obtenir l'email
            Cursor cursor = dbHelper.getUserDetails(currentUsername);
            if (cursor != null && cursor.moveToFirst()) {
                // Supposons que la colonne d'email est la deuxième colonne de votre curseur
                String email = cursor.getString(cursor.getColumnIndex("email")); // Assurez-vous que "email" correspond au nom de votre colonne
                tvCurrentEmail.setText(String.format("E-mail actuel: %s", email));
                cursor.close();
            }
        }

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AccountActivity.this)
                        .setTitle("Confirmer la mise à jour") // Titre du pop-up
                        .setMessage("Êtes-vous sûr de vouloir modifier vos informations ?") // Message affiché dans le pop-up
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // L'utilisateur confirme vouloir faire la mise à jour
                                String newUsername = etNewUsername.getText().toString();
                                String newPassword = etNewPassword.getText().toString();

                                if(updateUserInDatabase(newUsername, newPassword)) {
                                    Toast.makeText(AccountActivity.this, "Informations mises à jour avec succès!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(AccountActivity.this, "Échec de la mise à jour des informations.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, null) // Si l'utilisateur sélectionne "Non", rien ne se passe
                        .setIcon(android.R.drawable.ic_dialog_alert) // Icône d'alerte
                        .show();
            }
        });

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent homeIntent = new Intent(AccountActivity.this, MainActivity.class);
                    startActivity(homeIntent);
                    return true;
                case R.id.navigation_specific:
                    Intent accountIntent = new Intent(AccountActivity.this, AccountActivity.class);
                    startActivity(accountIntent);
                    return true;
            }
            return false;
        });

    }



    private boolean updateUserInDatabase(String newUsername, String newPassword) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", newUsername);
        values.put("password", newPassword);

        String currentUsername = getCurrentUsername(); // Utilise la nouvelle méthode implémentée

        if (currentUsername == null) {
            Toast.makeText(this, "Erreur : Utilisateur non identifié.", Toast.LENGTH_SHORT).show();
            return false;
        }

        String selection = "username = ?";
        String[] selectionArgs = { currentUsername };

        int count = db.update("users", values, selection, selectionArgs);

        if (count > 0) {
            // Mise à jour réussie, enregistrez le nouveau nom d'utilisateur comme actuel
            saveCurrentUser(newUsername);
        }

        return count > 0;
    }


}