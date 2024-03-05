package com.example.m8uf2avaluable2radio;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;

public class SecondActivity extends AppCompatActivity {

    // MediaPlayer de la radio
    private MediaPlayer player;

    // URL de la emisora
    private String radioURL;

    // Nombre emisora
    private TextView nombre;

    // Descripcion emisora
    private TextView descripcion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Recogemos intent para obtener información (nombre, descripción y URL)
        Intent intent = getIntent();

        // Recibir URL
        radioURL = intent.getStringExtra("url");

        // Recibir nombre
        nombre = findViewById(R.id.nombre_emisora_textView);
        nombre.setText(intent.getStringExtra("nombre"));

        // Recibir descripción
        descripcion = findViewById(R.id.descripcion_emisora_textView);
        descripcion.setText(intent.getStringExtra("descripcion"));

        // Comprueba si se tienen los permisos de acceso a internet
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                == PackageManager.PERMISSION_GRANTED) {
            // Si se tienen los permisos, realiza las acciones necesarias

            // Inicializar y preparar el MediaPlayer
            this.player = new MediaPlayer();
            try {
                player.setDataSource(radioURL);
                player.prepareAsync(); // Preparación asíncrona
                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.start(); // Iniciar reproducción
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Si no se tienen los permisos, solicita al usuario que los conceda
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    1);
        }



    }

    // Método para reproducir la emisora de radio
    public void soundPlay(View view) {
        if (player != null && !player.isPlaying()) {
            player.start();
        }
    }

    // Método para pausar la emisora de radio
    public void soundPause(View view) {
        if (player != null && player.isPlaying()) {
            player.pause();
        }
    }

    // Liberar recursos del MediaPlayer cuando se destruye la actividad
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}
