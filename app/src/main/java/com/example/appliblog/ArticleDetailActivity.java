package com.example.appliblog;

// Importation des classes nécessaires.
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

// L'objectif de ce code est de définir une activité qui affiche les détails d'un article,
// son titre, son contenu, et une image associée, en utilisant les données passées via Intent.
public class ArticleDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Définit le fichier de layout XML à utiliser pour l'interface utilisateur de cette activité.
        setContentView(R.layout.activity_article_detail);

        // Récupération de l'Intent qui a démarré cette activité et extraction des données (titre, URL de l'image, contenu).
        Intent intent = getIntent();
        String title = intent.getStringExtra("articleTitle");
        String imageUrl = intent.getStringExtra("articleImageUrl");
        String content = intent.getStringExtra("articleContent");

        // Recherche des éléments de vue dans le layout par leur ID.
        TextView titleTextView = findViewById(R.id.detailArticleTitleTextView);
        ImageView imageView = findViewById(R.id.detailArticleImageView);
        TextView contentTextView = findViewById(R.id.detailArticleContentTextView);

        // Mise à jour des vues avec les données de l'article : titre et contenu.
        titleTextView.setText(title);
        contentTextView.setText(content);

        // Utilisation de Glide pour charger l'image de l'article depuis son URL.
        Glide.with(this)
                .load(imageUrl) // Charge l'image à partir de l'URL.
                .apply(new RequestOptions().error(R.drawable.image_error))
                .into(imageView); // Destination de l'image chargée.
    }
}
