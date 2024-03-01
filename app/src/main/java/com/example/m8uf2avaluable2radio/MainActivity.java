package com.example.m8uf2avaluable2radio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtener referencias a los ImageButtons
        ImageButton catalunyaRadioButton = findViewById(R.id.imageButton);
        ImageButton catalunyaInformacioButton = findViewById(R.id.imageButton2);
        ImageButton catalunyaMusicaButton = findViewById(R.id.imageButton3);
        ImageButton icatFMButton = findViewById(R.id.imageButton4);

        // Configurar clic listeners para cada ImageButton
        catalunyaRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirSegundaActividad("Catalunya Radio", "Descripción de Catalunya Radio", getString(R.string.catalunya_radio_url));
            }
        });

        catalunyaInformacioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirSegundaActividad("Catalunya Informació", "Descripción de Catalunya Informació",  getString(R.string.catalunya_informacio_url));
            }
        });

        catalunyaMusicaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirSegundaActividad("Catalunya Música", "Descripción de Catalunya Música", getString(R.string.catalunya_musica_url));
            }
        });

        icatFMButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirSegundaActividad("iCat FM", "Descripción de iCat FM", getString(R.string.icat_fm_url));
            }
        });
    }

    private void abrirSegundaActividad(String nombreEmisora, String descripcionEmisora, String streamingUrl) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra("nombre_emisora", nombreEmisora);
        intent.putExtra("descripcion_emisora", descripcionEmisora);
        intent.putExtra("streaming_url", streamingUrl); // Agregar la URL del streaming al Intent
        startActivity(intent);
    }
}
