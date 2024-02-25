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
import com.bumptech.glide.request.RequestOptions;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private Context context;
    private List<Article> articles;

    public ArticleAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.article_layout, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.titleTextView.setText(article.getTitle());
        holder.contentTextView.setText(article.getContent());

        // Assurez-vous d'utiliser holder.imageView ici
        Glide.with(holder.itemView.getContext())
                .load(article.getImageUrl()) // URL de votre image
                .error(R.drawable.image_error) // Image d'erreur à utiliser si le chargement échoue
                .into(holder.imageView); // Utilisez holder.imageView

        // Ajouter un gestionnaire de clics
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ArticleDetailActivity.class);
                intent.putExtra("articleId", article.getId());
                intent.putExtra("articleTitle", article.getTitle());
                intent.putExtra("articleImageUrl", article.getImageUrl());
                intent.putExtra("articleContent", article.getContent());
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return articles.size();
    }

    // Ajoutez cette méthode pour mettre à jour la liste des articles dans l'adapter
    public void setArticles(List<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        ImageView imageView;
        TextView contentTextView;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.articleTitleTextView);
            imageView = itemView.findViewById(R.id.articleImageView);
            contentTextView = itemView.findViewById(R.id.articleContentTextView);
        }
    }
}