package com.example.mediassist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class PrescriptionActive extends AppCompatActivity {

    private ImageButton importButton, shareButton, backButton;
    private TextView fileNameText;
    private LinearLayout importedFileContainer;
    private Uri selectedFileUri = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Remplace par ton vrai layout si besoin

        // Initialisation des vues
        importButton = findViewById(R.id.importButton);
        shareButton = findViewById(R.id.shareButton);
        backButton = findViewById(R.id.backButton);
        fileNameText = findViewById(R.id.fileNameText);
        importedFileContainer = findViewById(R.id.importedFileContainer);

        // Action retour
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(PrescriptionActive.this, AccueilActivity.class);
            startActivity(intent);
            finish();
        });

        // Gestion de l'import
        ActivityResultLauncher<String> filePickerLauncher =
                registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                    if (uri != null) {
                        selectedFileUri = uri;
                        String name = uri.getLastPathSegment();
                        fileNameText.setText(name != null ? name : "Document importé");
                        importedFileContainer.setVisibility(LinearLayout.VISIBLE);
                        Toast.makeText(this, "Fichier importé", Toast.LENGTH_SHORT).show();
                    }
                });

        importButton.setOnClickListener(v -> {
            filePickerLauncher.launch("*/*");
        });

        // Partager le fichier
        shareButton.setOnClickListener(v -> {
            if (selectedFileUri != null) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("*/*");
                shareIntent.putExtra(Intent.EXTRA_STREAM, selectedFileUri);
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(shareIntent, "Partager le fichier via"));
            } else {
                Toast.makeText(this, "Aucun fichier à partager", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
