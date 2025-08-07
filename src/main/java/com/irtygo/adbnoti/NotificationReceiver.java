package com.longo.news;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.util.Random;

public class NotificationReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "adb_channel";
    private final Random random = new Random();

    private void showLogNotification(Context context, String message) {
        String logChannelId = "log_channel";
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(logChannelId, "Log Channel", NotificationManager.IMPORTANCE_LOW);
            nm.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, logChannelId)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle("Broadcast Log")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setAutoCancel(true);

        nm.notify(9999, builder.build());
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        String text = intent.getStringExtra("text");
        String prio = intent.getStringExtra("prio");

        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        if ("low".equalsIgnoreCase(prio)) {
            importance = NotificationManager.IMPORTANCE_LOW;
        } else if ("high".equalsIgnoreCase(prio)) {
            importance = NotificationManager.IMPORTANCE_HIGH;
        }

        NotificationManager notificationManager =
            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID, "ADB Channel", importance);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title != null ? title : "ADB Notification")
            .setContentText(text != null ? text : "")
            .setPriority(importance)
            .setAutoCancel(true);

        int notificationId = random.nextInt(Integer.MAX_VALUE);

        notificationManager.notify(notificationId, builder.build());
    }
}
