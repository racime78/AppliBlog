package com.example.appliblog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ArticleDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        // Récupération des données de l'intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("articleTitle");
        String imageUrl = intent.getStringExtra("articleImageUrl");
        String content = intent.getStringExtra("articleContent");

        // Trouver les vues par leur id et les peupler avec les données de l'article
        TextView titleTextView = findViewById(R.id.detailArticleTitleTextView);
        ImageView imageView = findViewById(R.id.detailArticleImageView);
        TextView contentTextView = findViewById(R.id.detailArticleContentTextView);

        titleTextView.setText(title);
        contentTextView.setText(content);

        // Utilisez Glide pour charger l'image depuis imageUrl dans imageView
        Glide.with(this)
                .load(imageUrl)
                .apply(new RequestOptions().error(R.drawable.image_error)) // Utilisez une image d'erreur en cas d'échec du chargement
                .into(imageView);
    }
}
