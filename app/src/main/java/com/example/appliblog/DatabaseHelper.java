package com.example.appliblog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Gère la base de données de l'application, y compris la création, la mise à jour,
 * et les opérations CRUD sur les tables des utilisateurs et des articles.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    // Informations de la base de données
    private static final String DATABASE_NAME = "UserDatabase";
    private static final int DATABASE_VERSION = 3;

    // Définition de la table des utilisateurs
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_EMAIL = "email";

    // Définition de la table des articles
    public static final String TABLE_ARTICLES = "articles";
    public static final String COLUMN_ARTICLE_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_IMAGE_URL = "imageUrl";
    public static final String COLUMN_CONTENT = "content";

    // Commande SQL pour créer la table des utilisateurs
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT, " +
                    COLUMN_EMAIL + " TEXT" +
                    ")";

    // Commande SQL pour créer la table des articles
    private static final String TABLE_ARTICLES_CREATE =
            "CREATE TABLE " + TABLE_ARTICLES + " (" +
                    COLUMN_ARTICLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_IMAGE_URL + " TEXT, " +
                    COLUMN_CONTENT + " TEXT" +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE); // Création de la table des utilisateurs
        db.execSQL(TABLE_ARTICLES_CREATE); // Création de la table des articles
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Suppression des anciennes tables si elles existent
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLES);
        // Recréation des tables
        onCreate(db);
    }

    /**
     * Vérifie si un utilisateur existe dans la base de données.
     */
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_ID},
                COLUMN_USERNAME + "=?" + " AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    /**
     * Récupère les détails d'un utilisateur spécifique.
     */
    public Cursor getUserDetails(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_USERS, new String[]{COLUMN_USERNAME, COLUMN_EMAIL},
                COLUMN_USERNAME + "=?", new String[]{username}, null, null, null);
    }

    /**
     * Ajoute un nouvel article à la base de données.
     */
    public void addArticle(String title, String imageUrl, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_IMAGE_URL, imageUrl);
        values.put(COLUMN_CONTENT, content);
        db.insert(TABLE_ARTICLES, null, values);
        db.close();
    }

    /**
     * Récupère tous les articles de la base de données.
     */
    public Cursor getAllArticles() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_ARTICLES, new String[]{COLUMN_ARTICLE_ID, COLUMN_TITLE, COLUMN_IMAGE_URL, COLUMN_CONTENT},
                null, null, null, null, null);
    }
}
