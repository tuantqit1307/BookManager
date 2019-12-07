package com.poly.bookmanager.Helper;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;


import com.poly.bookmanager.MainActivity;
import com.poly.bookmanager.R;

public class NotificationHelper extends ContextWrapper {
    NotificationChannel edmtChannel = null;
    Notification.Builder builder = null;
    private static final String EDMT_CHANNEL_ID = "com.poly.bookmanager";
    private static final String EDMT_CHANNEL_NAME = "Book Manager";
    private NotificationManager manager;

    public NotificationHelper (Context base) {
        super(base);
        createChannels();
    }

    private void createChannels() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            edmtChannel = new NotificationChannel(EDMT_CHANNEL_ID,EDMT_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            edmtChannel.enableLights(true);
            edmtChannel.enableVibration(true);
            edmtChannel.setLightColor(Color.GREEN);
            edmtChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            getManager().createNotificationChannel(edmtChannel);
        }

    }

    public NotificationManager getManager() {
        if(manager == null)
            manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;
    }

    public Notification.Builder getEDMTChannelNotification(String title,String body, String textBody) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            builder = new Notification.Builder(getApplicationContext(), EDMT_CHANNEL_ID)
                    .setContentText(body)
                    .setFullScreenIntent(pendingIntent, true)
                    .setContentTitle(title)
                    .setSmallIcon(R.drawable.icon)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.totalbook))
                    .setStyle(new Notification.BigTextStyle().bigText(textBody))
                    .setAutoCancel(true);
        }
        return builder;
        }

}
