package com.example.rodrigo.serviceapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rodrigo.serviceapplication.Service.NotificationManagerService;

/**
 * Activity principal
 */
public class MainActivity extends AppCompatActivity {

    //Variables globales
    private static final String TAG = "MainActivity";
    private Button btnStartService, btnStopService;
    private TextView txtInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtInfo = (TextView) findViewById(R.id.txtInfo);
        btnStartService = (Button) findViewById(R.id.btnStartService);
        btnStopService = (Button) findViewById(R.id.btnStopService);

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
    }
}
