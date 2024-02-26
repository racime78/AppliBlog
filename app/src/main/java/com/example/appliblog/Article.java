package com.example.appliblog;

/**
 * Modèle de données pour un article.
 * Représente un article avec un identifiant unique, un titre, une URL d'image, et du contenu.
 */
public class Article {
    private int id; // Identifiant unique pour chaque article, utile pour le stockage en base de données
    private String title; // Titre de l'article
    private String imageUrl; // URL de l'image associée à l'article
    private String content; // Contenu textuel de l'article

    /**
     * Constructeur pour initialiser une instance d'Article avec tous les attributs nécessaires.
     * @param id Identifiant unique de l'article.
     * @param title Titre de l'article.
     * @param imageUrl URL de l'image associée à l'article.
     * @param content Contenu de l'article.
     */
    public Article(int id, String title, String imageUrl, String content) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.content = content;
    }

    // Getters et Setters pour accéder et modifier les propriétés de l'article

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Méthode toString pour afficher les informations de l'article de manière lisible,
     * utile pour le débogage et le log.
     * @return une chaîne de caractères représentant l'article.
     */
    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
