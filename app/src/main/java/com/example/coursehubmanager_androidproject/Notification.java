package com.example.coursehubmanager_androidproject;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class Notification {
    private Context context;
    NotificationManager manager;
    private final String CHANNELID = "1";
    private final String CHANNELNAME = "CHANNEL 1";


    public Notification(Context context) {
        this.context = context;
    }

    public void createNotification() {
        Intent intent = new Intent(context, HomeActivity.class);
        Intent intent2 = new Intent(context, HomeActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(context, 1, intent2, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNELID);
        builder.setContentTitle("Notification");
        builder.setContentText("A new course has been added");
        builder.setSmallIcon(R.drawable.iconbell);
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);
        builder.addAction(R.drawable.ic_launcher_foreground, "send", pendingIntent2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNELID, CHANNELNAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Channel 1 description");
            channel.enableLights(true);
            channel.setShowBadge(true);
            channel.enableVibration(true);
            manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        manager.notify(1, builder.build());
    }
}
