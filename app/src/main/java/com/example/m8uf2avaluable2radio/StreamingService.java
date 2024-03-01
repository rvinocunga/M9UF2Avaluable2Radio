package com.example.m8uf2avaluable2radio;

import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.m8uf2avaluable2radio.R;

public class StreamingService extends Service {
    private static final int NOTIFICATION_ID = 1;

    private MediaPlayer mediaPlayer;
    private String streamingUrl;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.hasExtra("streaming_url")) {
            streamingUrl = intent.getStringExtra("streaming_url");

            // Inicializar el MediaPlayer
            mediaPlayer = new MediaPlayer();

            try {
                // Establecer la URL del streaming
                mediaPlayer.setDataSource(streamingUrl);

                // Configurar atributos de audio (opcional, pero recomendado para versiones de Android 8.0 y superiores)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build());
                } else {
                    mediaPlayer.setAudioStreamType(AudioAttributes.CONTENT_TYPE_MUSIC);
                }

                // Preparar el MediaPlayer
                mediaPlayer.prepareAsync();

                // Mostrar una notificación en primer plano
                startForeground(NOTIFICATION_ID, createNotification());

                // Manejar eventos de preparación del MediaPlayer
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });
            } catch (Exception e) {
                Toast.makeText(this, "Error al reproducir el streaming", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                stopSelf(); // Detener el servicio si hay un error
            }
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // Método para crear una notificación en primer plano
    private Notification createNotification() {
        Intent notificationIntent = new Intent(this, SecondActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        return new NotificationCompat.Builder(this, "default")
                .setContentTitle("Reproduciendo streaming en directo")
                .setContentText("Escuchando...")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .build();
    }
}
