package com.example.m8uf2avaluable2radio;

import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.IBinder;
import androidx.annotation.Nullable;

public class StreamingService extends Service {
    private MediaPlayer mediaPlayer;
    private String streamingUrl;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.hasExtra("streaming_url")) {
            streamingUrl = intent.getStringExtra("streaming_url");

            // Inicializar el MediaPlayer
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build());

            try {
                // Establecer la URL del streaming
                mediaPlayer.setDataSource(streamingUrl);

                // Preparar el MediaPlayer
                mediaPlayer.prepareAsync();

                // Manejar eventos de ciclo de vida del MediaPlayer
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        // Reproducir el audio cuando esté preparado
                        mediaPlayer.start();
                    }
                });
                mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        // Manejar errores durante la preparación o reproducción
                        return false;
                    }
                });
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        // Manejar la finalización de la reproducción
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
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
}
