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

/**
 * Activité permettant à l'utilisateur de gérer son compte, notamment
 * de modifier son nom d'utilisateur et son mot de passe.
 */
public class AccountActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper; // Helper pour interagir avec la base de données SQLite
    private EditText etNewUsername; // Champ de texte pour le nouveau nom d'utilisateur
    private EditText etNewPassword; // Champ de texte pour le nouveau mot de passe
    private Button btnSaveChanges; // Bouton pour enregistrer les modifications

    /**
     * Enregistre le nom d'utilisateur actuel dans les préférences partagées.
     * @param username Le nom d'utilisateur à enregistrer.
     */
    private void saveCurrentUser(String username) {
        SharedPreferences sharedPreferences = getSharedPreferences("userSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.apply();
    }

    /**
     * Récupère le nom d'utilisateur actuel depuis les préférences partagées.
     * @return Le nom d'utilisateur actuel, ou `null` si non trouvé.
     */
    public String getCurrentUsername() {
        SharedPreferences sharedPreferences = getSharedPreferences("userSession", MODE_PRIVATE);
        return sharedPreferences.getString("username", null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Initialisation du helper de la base de données et des composants de l'interface utilisateur
        dbHelper = new DatabaseHelper(this);
        etNewUsername = findViewById(R.id.etNewUsername);
        etNewPassword = findViewById(R.id.etNewPassword);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        // Affichage des informations actuelles de l'utilisateur
        TextView tvCurrentUsername = findViewById(R.id.tvCurrentUsername);
        TextView tvCurrentEmail = findViewById(R.id.tvCurrentEmail);
        displayCurrentUserInfo(tvCurrentUsername, tvCurrentEmail);

        // Configuration du listener du bouton pour sauvegarder les changements
        setupSaveChangesButton();

        // Configuration de la navigation inférieure
        setupBottomNavigationView();
    }

    /**
     * Affiche les informations actuelles de l'utilisateur dans l'interface.
     */
    private void displayCurrentUserInfo(TextView tvCurrentUsername, TextView tvCurrentEmail) {
        String currentUsername = getCurrentUsername();
        if (currentUsername != null) {
            tvCurrentUsername.setText(String.format("Nom d'utilisateur actuel: %s", currentUsername));
            Cursor cursor = dbHelper.getUserDetails(currentUsername);
            if (cursor != null && cursor.moveToFirst()) {
                String email = cursor.getString(cursor.getColumnIndex("email"));
                tvCurrentEmail.setText(String.format("E-mail actuel: %s", email));
                cursor.close();
            }
        }
    }

    /**
     * Configure le bouton pour enregistrer les modifications apportées par l'utilisateur.
     */
    private void setupSaveChangesButton() {
        btnSaveChanges.setOnClickListener(v -> new AlertDialog.Builder(AccountActivity.this)
                .setTitle("Confirmer la mise à jour")
                .setMessage("Êtes-vous sûr de vouloir modifier vos informations ?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> saveChanges())
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show());
    }

    /**
     * Sauvegarde les changements dans la base de données après confirmation de l'utilisateur.
     */
    private void saveChanges() {
        String newUsername = etNewUsername.getText().toString();
        String newPassword = etNewPassword.getText().toString();

        if (updateUserInDatabase(newUsername, newPassword)) {
            Toast.makeText(AccountActivity.this, "Informations mises à jour avec succès!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AccountActivity.this, "Échec de la mise à jour des informations.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Met à jour les informations de l'utilisateur dans la base de données.
     * @param newUsername Le nouveau nom d'utilisateur.
     * @param newPassword Le nouveau mot de passe.
     * @return `true` si la mise à jour a réussi, sinon `false`.
     */
    private boolean updateUserInDatabase(String newUsername, String newPassword) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", newUsername);
        values.put("password", newPassword);

        String currentUsername = getCurrentUsername();

        if (currentUsername == null) {
            Toast.makeText(this, "Erreur : Utilisateur non identifié.", Toast.LENGTH_SHORT).show();
            return false;
        }

        int count = db.update("users", values, "username = ?", new String[]{currentUsername});

        if (count > 0) {
            saveCurrentUser(newUsername);
        }

        return count > 0;
    }

    /**
     * Configure la navigation inférieure.
     */
    private void setupBottomNavigationView() {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    startActivity(new Intent(AccountActivity.this, MainActivity.class));
                    return true;
                case R.id.navigation_specific:
                    startActivity(new Intent(AccountActivity.this, AccountActivity.class));
                    return true;
                default:
                    return false;
            }
        });
    }
}
