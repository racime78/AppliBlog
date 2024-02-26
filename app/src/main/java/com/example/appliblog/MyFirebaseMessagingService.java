package com.example.appliblog;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Service pour gérer les messages entrants de Firebase Cloud Messaging (FCM).
 * Cela inclut la réception de messages de données et de notifications quand l'application est en premier plan.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FCMService";

    /**
     * Appelé lorsque le message FCM est reçu.
     *
     * @param remoteMessage L'objet message reçu de FCM.
     */
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // Vérifier si le message contient des données
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Vérifier si le message contient une notification
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            // Ici, vous pouvez personnaliser la façon dont vous voulez traiter la notification
            // Par exemple, vous pouvez utiliser une notification Android pour la notifier à l'utilisateur
        }
    }

    /**
     * Appelé si le Token FCM est mis à jour. Ceci peut se produire si l'ancien token
     * a été compromis ou lorsqu'un nouveau token est généré.
     *
     * @param token Le nouveau token FCM pour l'appareil.
     */
    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "Refreshed token: " + token);
        // Envoyer le token à votre serveur pour le maintenir à jour
        sendRegistrationToServer(token);
    }

    /**
     * Envoyer le token FCM à votre serveur d'application pour le garder à jour.
     *
     * @param token Le nouveau token FCM généré.
     */
    private void sendRegistrationToServer(String token) {
        // Implémentez votre logique pour envoyer le token au serveur d'application ici
        Log.d(TAG, "sendRegistrationToServer: " + token);
    }
}
