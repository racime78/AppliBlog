package com.example.appliblog;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

/**
 * Adapter pour afficher une liste d'articles dans un RecyclerView.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private Context context; // Contexte pour accéder aux ressources et démarrer une nouvelle activité
    private List<Article> articles; // Liste des articles à afficher

    // Constructeur de l'adapter
    public ArticleAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    // Crée de nouveaux ViewHolder pour les éléments
    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.article_layout, parent, false);
        return new ArticleViewHolder(view);
    }

    // Remplit le contenu d'un ViewHolder avec les données d'un article à une position donnée
    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.titleTextView.setText(article.getTitle());

        // Chargement de l'image avec Glide
        Glide.with(holder.itemView.getContext())
                .load(article.getImageUrl())
                .error(R.drawable.image_error) // Image par défaut en cas d'erreur
                .into(holder.imageView);

        // Gestion du clic sur un élément pour ouvrir les détails de l'article
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ArticleDetailActivity.class);
            intent.putExtra("articleId", article.getId());
            intent.putExtra("articleTitle", article.getTitle());
            intent.putExtra("articleImageUrl", article.getImageUrl());
            intent.putExtra("articleContent", article.getContent());
            context.startActivity(intent);
        });
    }

    // Retourne le nombre total d'articles dans la liste
    @Override
    public int getItemCount() {
        return articles.size();
    }

    // Met à jour la liste des articles et notifie l'adapter d'un jeu de données changé
    public void setArticles(List<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    // ViewHolder interne pour contenir les vues d'un seul élément de la liste
    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        ImageView imageView;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.articleTitleTextView);
            imageView = itemView.findViewById(R.id.articleImageView);
        }
    }
}
