package com.example.mediassist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PageMedicaments extends AppCompatActivity {

    ImageButton backButton, menuButton;
    FloatingActionButton fabAdd;
    EditText searchInput;
    LinearLayout contentContainer;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Assure-toi que ton fichier XML s'appelle bien activity_main.xml

        // Initialisation des vues
        backButton = findViewById(R.id.backButton);
        menuButton = findViewById(R.id.menuButton);
        fabAdd = findViewById(R.id.fabAdd);
        searchInput = findViewById(R.id.search_medications);
        contentContainer = findViewById(R.id.rootLayout);

        // Action bouton retour
        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(PageMedicaments.this, PreviousActivity.class);
            startActivity(intent);
            finish(); // Facultatif selon la logique
        });

        // Action bouton ajout "+"
        fabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(PageMedicaments.this, AddMedicationActivity.class);
            startActivity(intent);
        });

        // Action de recherche
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                afficherResultatRecherche(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void afficherResultatRecherche(String query) {
        // Efface les anciens résultats (si dynamiques)
        @SuppressLint("WrongViewCast") LinearLayout resultContainer = findViewById(R.id.rootLayout);

        // Supprimer les anciens résultats
        for (int i = resultContainer.getChildCount() - 1; i >= 0; i--) {
            View child = resultContainer.getChildAt(i);
            if (child instanceof TextView && ((TextView) child).getText().toString().startsWith("Résultat :")) {
                resultContainer.removeView(child);
            }
        }

        // Exemple de mot-clé
        if (query.equalsIgnoreCase("doliprane")) {
            TextView resultText = new TextView(this);
            resultText.setText("Résultat : Doliprane 500mg");
            resultText.setTextSize(16);
            resultText.setTextColor(getResources().getColor(android.R.color.black));
            resultText.setPadding(32, 16, 0, 0);
            resultContainer.addView(resultText);
        }
    }
}
