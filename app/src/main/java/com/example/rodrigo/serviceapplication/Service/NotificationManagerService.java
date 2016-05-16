package com.example.rodrigo.serviceapplication.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.rodrigo.serviceapplication.R;

/**
 *
 */
public class NotificationManagerService extends Service {

    private static final String TAG = "NotificationService";

    @Override
    public void onCreate() {
        Log.i(TAG, "Service onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        Log.i(TAG, "Service onStartCommand");

        new Thread(new Runnable() {
            @Override
            public void run() {
                //Creamos el objeto notificationManager
                Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_android);
                Notification.Builder builder = new Notification.Builder(getApplicationContext())
                        .setContentTitle(intent.getExtras().getString("Title").toString())
                        .setContentText(intent.getExtras().getString("Content").toString())
                        .setSmallIcon(R.drawable.ic_android)
                        .setLargeIcon(icon);
                Notification.BigTextStyle notification = new Notification.BigTextStyle(builder)
                        .setBigContentTitle("Service Application")
                        .bigText(intent.getExtras().getString("Content").toString())
                        .setSummaryText("Android");
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                int notificationRef = 1;
                notificationManager.notify(notificationRef, notification.build());
            }
        }).start();
        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "Service onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "Service onDestroy");
    }
}
