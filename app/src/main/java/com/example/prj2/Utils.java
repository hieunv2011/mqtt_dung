package com.example.prj2;

//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.content.Context;
//import android.os.Build;
//
//import androidx.core.app.NotificationCompat;
//
//import java.util.Random;
//
//public class Utils {
//    public static final String TAG = "Kz";
//    public static  void showNotification(Context context, String title, String body) {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "hieu");
//        builder.setSmallIcon(R.drawable.ic_launcher_background);
//        builder.setContentTitle(title);
//        builder.setContentText(body);
//        builder.setPriority(NotificationCompat.PRIORITY_MAX);
//        //style
//        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
//        bigTextStyle.bigText(title);
//        bigTextStyle.setBigContentTitle(title);
//        bigTextStyle.setSummaryText(title);
//
//        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            String channelId="hieu";
//            NotificationChannel channel = new NotificationChannel(channelId,"Hieu Channel",
//                    NotificationManager.IMPORTANCE_HIGH);
//            manager.createNotificationChannel(channel);
//            builder.setChannelId(channelId);
//        }
//        manager.notify(new Random().nextInt(),builder.build());
//    }
//}
import android.media.MediaPlayer;
import android.os.Build;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import androidx.core.app.NotificationCompat;
import android.content.Context;
import java.util.Random;

public class Utils {
    public static final String TAG = "Kz";

    public static void showNotification(Context context, String title, String body) {
        // Tạo thông báo
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "hieu");
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentTitle(title);
        builder.setContentText(body);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);

        // Tạo Notification Style
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.bigText(title);
        bigTextStyle.setBigContentTitle(title);
        bigTextStyle.setSummaryText(title);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Kiểm tra phiên bản Android và tạo Notification Channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "hieu";
            NotificationChannel channel = new NotificationChannel(channelId, "Hieu Channel", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }

        // Hiển thị thông báo
        manager.notify(new Random().nextInt(), builder.build());

        // Phát âm thanh khi có thông báo
        try {
            // Sử dụng MediaPlayer để phát âm thanh từ file raw
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.sound); // Thay 'sound' bằng tên file âm thanh trong thư mục raw
            mediaPlayer.start(); // Phát âm thanh
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

