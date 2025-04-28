package com.example.mediassist;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AppointmentsActivity extends AppCompatActivity {

    private LinearLayout appointmentsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        // Initialisation des vues
        appointmentsContainer = findViewById(R.id.appointmentsContainer);

        // Configuration de la Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Bouton menu trois points
        findViewById(R.id.menuButton).setOnClickListener(v -> {
            // Ouvrir un menu contextuel
            showOptionsMenu();
        });

        // Bouton flottant pour ajouter un rendez-vous
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(AppointmentsActivity.this, AddAppointmentActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String note = data.getStringExtra("note");
            String date = data.getStringExtra("date");
            String category = data.getStringExtra("category");

            addAppointmentCard(note, date, category);
        }
    }

    private void addAppointmentCard(String note, String date, String category) {
        View appointmentCard = getLayoutInflater().inflate(R.layout.item_appointment, null);

        TextView noteView = appointmentCard.findViewById(R.id.appointmentNote);
        TextView dateView = appointmentCard.findViewById(R.id.appointmentDate);
        TextView categoryView = appointmentCard.findViewById(R.id.appointmentCategory);

        noteView.setText(note);
        dateView.setText("Date: " + date);
        categoryView.setText("Category: " + category);

        appointmentsContainer.addView(appointmentCard, 0);
    }

    private void showOptionsMenu() {
        // Implémentation du menu contextuel
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}