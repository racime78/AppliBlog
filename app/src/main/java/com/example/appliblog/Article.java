package com.example.appliblog;

public class Article {
    private int id; // Identifiant unique pour chaque article, utile si vous stockez les articles dans une base de données
    private String title; // Titre de l'article
    private String imageUrl; // URL de l'image associée à l'article
    private String content; // Contenu de l'article

    // Constructeur avec tous les paramètres
    public Article(int id, String title, String imageUrl, String content) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.content = content;
    }

    // Getters et Setters
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

    // Méthode toString() pour afficher les informations de l'article sous forme de chaîne de caractères, utile pour le débogage
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

