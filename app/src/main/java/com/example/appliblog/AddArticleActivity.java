package com.example.appliblog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddArticleActivity extends AppCompatActivity {
    private EditText editTextTitle, editTextImageUrl, editTextContent;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);

        editTextTitle = findViewById(R.id.editTextArticleTitle);
        editTextImageUrl = findViewById(R.id.editTextArticleImageUrl);
        editTextContent = findViewById(R.id.editTextArticleContent);
        buttonSubmit = findViewById(R.id.buttonSubmitArticle);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitArticle();
            }
        });
    }

    private void submitArticle() {
        String title = editTextTitle.getText().toString().trim();
        String imageUrl = editTextImageUrl.getText().toString().trim();
        String content = editTextContent.getText().toString().trim();

        // Vérifier que les champs ne sont pas vides
        if (!title.isEmpty() && !imageUrl.isEmpty() && !content.isEmpty()) {
            // Enregistrer l'article dans la base de données
            DatabaseHelper dbHelper = new DatabaseHelper(this);

            // Modifier ici pour utiliser les arguments attendus par addArticle
            dbHelper.addArticle(title, imageUrl, content);

            Toast.makeText(this, "Article ajouté", Toast.LENGTH_SHORT).show();
            finish(); // Fermer l'activité et revenir à la liste des articles
        } else {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        }
    }

}
