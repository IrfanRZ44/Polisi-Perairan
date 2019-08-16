package com.exomatik.irfanrz.kepolisian.Featured;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.exomatik.irfanrz.kepolisian.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by IrfanRZ on 07/08/2019.
 */

public class MyFirebaseMessaging extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String title = remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification().getBody();
        String clickAction = remoteMessage.getNotification().getClickAction();

        Intent intent = new Intent(clickAction);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationBuild = new NotificationCompat.Builder(this);
        notificationBuild.setContentTitle(title);
        notificationBuild.setContentText(message);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuild.setSmallIcon(R.mipmap.logo);
            notificationBuild.setLargeIcon(BitmapFactory.decodeResource(getResources(),
                    R.mipmap.logo));
            notificationBuild.setBadgeIconType(R.mipmap.logo);
            notificationBuild.setColor(getResources().getColor(R.color.blue2));
        } else {
            notificationBuild.setSmallIcon(R.mipmap.logo);
            notificationBuild.setLargeIcon(BitmapFactory.decodeResource(getResources(),
                    R.mipmap.logo));
            notificationBuild.setBadgeIconType(R.mipmap.logo);
        }


        notificationBuild.setAutoCancel(true);
        notificationBuild.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuild.build());

    }
}
