package com.example.mediassist;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configuration de l'interface edge-to-edge
        configureEdgeToEdge();

        setContentView(R.layout.activity_main);

        // Initialisation des vues
        initializeViews();

        // Exemple d'interaction avec l'image
        setupImageInteraction();
    }

    private void configureEdgeToEdge() {
        // 1. Autoriser le contenu à s'étendre sous les barres système
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        // 2. Configurer la couleur des icônes système (clair/sombre)
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(
                getWindow(),
                getWindow().getDecorView()
        );
        controller.setAppearanceLightStatusBars(false); // Icônes claires
        controller.setAppearanceLightNavigationBars(false); // Icônes claires
    }

    private void initializeViews() {
        // Récupération des références des vues

        ImageView centerImageView = findViewById(R.id.centerImage);
        TextView bottomTextView = findViewById(R.id.bottomText);

        // Configuration dynamique (exemple)

        bottomTextView.setText("Prenez-en soin chaque jour");

        // Option: Chargement d'image depuis les ressources
        centerImageView.setImageResource(R.drawable.mediass);
    }

    private void setupImageInteraction() {
        ImageView centerImage = findViewById(R.id.centerImage);
        centerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Exemple d'action au clic sur l'image
                TextView bottomText = findViewById(R.id.bottomText);
                bottomText.setText("Image cliquée !");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Code exécuté quand l'activité revient au premier plan
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Code exécuté quand l'activité passe en arrière-plan
    }
}