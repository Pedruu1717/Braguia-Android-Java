package com.braguia;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.braguia.ui.GeofenceHelper;
import com.braguia.ui.MainActivity;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "GeofenceBroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.e("GeofenceManager", "Erro ao processar geofence: " + geofencingEvent.getErrorCode());
            return;
        }

        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            // O usuário entrou na geofence, emitir notificação
            sendNotification(context, "Você entrou em um ponto de interesse");
        } else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            // O usuário saiu da geofence, emitir notificação
            sendNotification(context, "Você saiu de um ponto de interesse");
        }
    }

    private void sendNotification(Context context, String s) {

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "geofence_channel")
                .setSmallIcon(R.drawable.person_icon)
                .setContentTitle("Geofence Alert")
                .setContentText("message")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(0, builder.build());




    }
}
