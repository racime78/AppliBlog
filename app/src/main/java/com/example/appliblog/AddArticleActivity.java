package com.example.appliblog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activité pour ajouter un nouvel article dans l'application.
 * Permet à l'utilisateur de saisir un titre, une URL d'image, et le contenu de l'article.
 */
public class AddArticleActivity extends AppCompatActivity {
    // Déclarations des composants de l'interface utilisateur
    private EditText editTextTitle, editTextImageUrl, editTextContent;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article); // Définit le layout de l'activité

        // Initialisation des composants de l'interface utilisateur
        editTextTitle = findViewById(R.id.editTextArticleTitle);
        editTextImageUrl = findViewById(R.id.editTextArticleImageUrl);
        editTextContent = findViewById(R.id.editTextArticleContent);
        buttonSubmit = findViewById(R.id.buttonSubmitArticle);

        // Configuration du listener du bouton pour soumettre l'article
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitArticle(); // Appel de la méthode pour soumettre l'article
            }
        });
    }

    /**
     * Méthode pour soumettre un nouvel article à la base de données.
     * Vérifie que les champs ne sont pas vides avant de procéder à l'enregistrement.
     */
    private void submitArticle() {
        // Récupération des valeurs saisies par l'utilisateur
        String title = editTextTitle.getText().toString().trim();
        String imageUrl = editTextImageUrl.getText().toString().trim();
        String content = editTextContent.getText().toString().trim();

        // Vérification que les champs ne sont pas vides
        if (!title.isEmpty() && !imageUrl.isEmpty() && !content.isEmpty()) {
            DatabaseHelper dbHelper = new DatabaseHelper(this); // Instance de la classe d'aide pour la base de données
            dbHelper.addArticle(title, imageUrl, content); // Ajout de l'article dans la base de données

            Toast.makeText(this, "Article ajouté", Toast.LENGTH_SHORT).show(); // Notification de l'ajout réussi
            finish(); // Fermeture de l'activité pour revenir à l'affichage précédent
        } else {
            // Notification à l'utilisateur que tous les champs doivent être remplis
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        }
    }
}
