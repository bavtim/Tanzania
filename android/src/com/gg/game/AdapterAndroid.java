package com.gg.game;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.badlogic.gdx.Gdx;
import com.gg.game.utils.NotificationHandler;

//адаптер ввывода уведомлений
public class AdapterAndroid implements NotificationHandler {

    private Activity gameActivity;
    private Context context;

    public AdapterAndroid(Activity gameActivity) {
        this.gameActivity = gameActivity;
        context = this.gameActivity;
    }

    //метод ввывода уведомлений
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void showNotification(String title, String text) {
//для старых android
        if (Gdx.app.getVersion() <= 26) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setPriority(Notification.PRIORITY_DEFAULT);


            int notificationId = 1222;
            // Gets an instance of the NotificationManager service
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

            notificationManager.notify(notificationId, builder.build());
        } else {
//для новых android
            int notifyID = 1;
            String CHANNEL_ID = "my_channel_01";
            CharSequence name = title;
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            Notification notification = new Notification.Builder(context)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setChannelId(CHANNEL_ID)
                    .build();
            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(mChannel);

            mNotificationManager.notify(notifyID, notification);
        }


    }
}