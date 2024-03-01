package com.example.m8uf2avaluable2radio;import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    private Intent serviceIntent;
    private String streamingUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Obtener la URL del streaming del intent
        streamingUrl = getIntent().getStringExtra("streaming_url");

        // Configurar el botón de reproducción
        Button playButton = findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarReproduccion();
            }
        });

        // Inicializar el Intent para el servicio
        serviceIntent = new Intent(SecondActivity.this, StreamingService.class);
        serviceIntent.putExtra("streaming_url", streamingUrl);
    }

    private void iniciarReproduccion() {
        startService(serviceIntent);
    }
}
