package com.example.rodrigo.serviceapplication.Service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * IntentService
 */
public class NotificationManagerIntentService extends IntentService {

    private static final String TAG = "NotificationManagerIntentService";

    //Acciones asociadas al id del intent
    public static final String PROGRESS = "com.example.rodrigo.serviceapplication.progress";
    public static final String ENDTASK ="com.example.rodrigo.serviceapplication.endtask";

    //Constructor por defecto
    public NotificationManagerIntentService(){
        super("NotificationManagerIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int progressLength = intent.getExtras().getInt("progressLength",0);
        for (int i = 0; i <= progressLength; i++) {
            try {
                Thread.sleep(1000);
                //Progreso
                Intent progressIntent = new Intent();
                progressIntent.setAction(PROGRESS);
                progressIntent.putExtra("progress",i*10);
                sendBroadcast(progressIntent);
            } catch (InterruptedException e) {
                Log.e(TAG,e.getMessage());
            }
        }
        //Final de la tarea
        Intent endTaskIntent = new Intent();
        endTaskIntent.setAction(ENDTASK);
        sendBroadcast(endTaskIntent);
    }
}
