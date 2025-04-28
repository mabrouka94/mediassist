package com.example.mediassist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EmergencyContactActivity extends AppCompatActivity {

    private static final int ADD_CONTACT_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_emergency);

        // Configuration de la Toolbar
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Bouton flottant pour ajouter un contact
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(EmergencyContactActivity.this, AddEmergencyContactActivity.class);
            startActivityForResult(intent, ADD_CONTACT_REQUEST);
        });

        // Bouton menu trois points
        findViewById(R.id.menuButton).setOnClickListener(v -> {
            // Ici vous pouvez ajouter un menu contextuel
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CONTACT_REQUEST && resultCode == RESULT_OK) {
            // Rafraîchir la liste des contacts
            refreshContacts();
        }
    }

    private void refreshContacts() {
        // Implémentez la logique pour rafraîchir la liste des contacts
        // Par exemple, recharger les données depuis une base de données
    }
}