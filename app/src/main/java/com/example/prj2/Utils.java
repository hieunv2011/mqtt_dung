package com.example.prj2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.util.Random;

public class Utils {
    public static final String TAG = "Kz";
    public static  void showNotification(Context context, String title, String body) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "hieu");
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentTitle(title);
        builder.setContentText(body);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        //style
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.bigText(title);
        bigTextStyle.setBigContentTitle(title);
        bigTextStyle.setSummaryText(title);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelId="hieu";
            NotificationChannel channel = new NotificationChannel(channelId,"Hieu Channel",
                    NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }
        manager.notify(new Random().nextInt(),builder.build());
    }
}
