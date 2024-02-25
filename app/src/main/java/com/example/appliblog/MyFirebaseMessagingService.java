package com.example.appliblog;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FCMService";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        // Gérer ici le message de notification reçu
        // Par exemple, afficher les données du message dans le log
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Vérifier si le message contient une notification et l'afficher
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "Refreshed token: " + token);
        sendRegistrationToServer(token);
    }

    /**
     * Persister le token sur le serveur de l'application si nécessaire.
     *
     * @param token Le nouveau token FCM.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implémentez cette méthode pour envoyer le token à votre serveur d'application.
        Log.d(TAG, "sendRegistrationToServer: " + token);
    }
}
