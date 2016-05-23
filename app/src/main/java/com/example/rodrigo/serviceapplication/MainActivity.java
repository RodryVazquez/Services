package com.example.rodrigo.serviceapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodrigo.serviceapplication.Service.NotificationManagerIntentService;
import com.example.rodrigo.serviceapplication.Service.NotificationManagerService;

/**
 * Activity principal
 */
public class MainActivity extends AppCompatActivity {

    //Variables globales
    private static final String TAG = "MainActivity";
    private Button btnStartService, btnStopService, btnStartIntentService;
    private TextView txtInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Referenciamos de la vista
        txtInfo = (TextView) findViewById(R.id.txtInfo);
        btnStartService = (Button) findViewById(R.id.btnStartService);
        btnStopService = (Button) findViewById(R.id.btnStopService);
        btnStartIntentService = (Button) findViewById(R.id.btnStartIntentService);

        //Inicia el servicio
        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtInfo.setText("Service started");
                Intent intent = new Intent(MainActivity.this, NotificationManagerService.class);
                intent.putExtra("Title","News for android ATC");
                intent.putExtra("Content","This is alert from the service");
                startService(intent);
            }
        });

        //Detiene el servicio
        btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtInfo.setText("Service stopped");
                stopService(new Intent(MainActivity.this,NotificationManagerService.class));
            }
        });

        //Inicia el intent service
        btnStartIntentService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotificationManagerIntentService.class);
                intent.putExtra("progressLength",10);
                startService(intent);
            }
        });

        //Registramos el broadcast receiver
        IntentFilter filter = new IntentFilter();
        filter.addAction(NotificationManagerIntentService.PROGRESS);
        filter.addAction(NotificationManagerIntentService.ENDTASK);
        Receiver receiver = new Receiver();
        registerReceiver(receiver,filter);
    }

    //BroadcastReceiver
    private class Receiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            //Id de la notificacion
            int notificationRef = 1;
            //Creamos la notificacion
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(MainActivity.this)
                    .setContentTitle(getResources().getString(R.string.notification_title))
                    .setContentText(getResources().getString(R.string.download_title))
                    .setSmallIcon(R.drawable.ic_android);
            //Dependiendo del intent recibido mostramos el progreso o lo ocultamos
            if(intent.getAction().equals(NotificationManagerIntentService.PROGRESS)){
                //Progreso
                int incr = intent.getIntExtra("progress",0);
                //Mostramos el progressbar en la notificacion (max,progress,indeterminate)
                builder.setProgress(100,incr,false);
                notificationManager.notify(notificationRef,builder.build());
            }else if(intent.getAction().equals(NotificationManagerIntentService.ENDTASK)){
                //Añadimos un sonido a la notificacion
                Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                builder.setSound(notificationSound);
                builder.setContentText("Download Complete");
                //Ocultamos el progressbar
                builder.setProgress(0,0,false);
                notificationManager.notify(notificationRef,builder.build());
                Log.i(TAG,"Descarga exitosa");
            }
        }
    }
}
