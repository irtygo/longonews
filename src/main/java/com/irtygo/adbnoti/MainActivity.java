package com.longo.news;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import android.Manifest;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;
import android.os.Build;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import androidx.core.app.NotificationCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


public class MainActivity extends Activity {

    private static final int REQUEST_NOTIFICATION_PERMISSION = 1;
    private TextView logTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup UI: ScrollView with TextView inside
        ScrollView scrollView = new ScrollView(this);
        logTextView = new TextView(this);
        scrollView.addView(logTextView);
        setContentView(scrollView);

        log("App started");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        REQUEST_NOTIFICATION_PERMISSION);
            } else {
                log("POST_NOTIFICATIONS already granted");
                showTestNotification();
            }
        } else {
            showTestNotification();
        }
    }

    private void log(String message) {
        runOnUiThread(() -> {
            logTextView.append("LOG:" + message + "\n");
        });
    }

    private void showTestNotification() {
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String channelId = "test_channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Test Channel", NotificationManager.IMPORTANCE_HIGH);
            nm.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Self Test Notification")
                .setContentText("Notifications are working!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        nm.notify(12345, builder.build());

        log("Test notification shown");
        logTextView.append("Please close this app, News will come as notifcations.");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_NOTIFICATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                log("POST_NOTIFICATIONS permission granted");
                showTestNotification();
            } else {
                log("POST_NOTIFICATIONS permission denied");
            }
        }
    }

}
