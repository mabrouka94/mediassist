package com.example.mediassist;


import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.util.Calendar;

public class AddMedicationActivity extends AppCompatActivity {

    EditText nameEditText, typeEditText, freqEditText, doseEditText;
    Button timeButton, saveButton;
    ImageView photoImageView;
    ImageButton backArrow;
    String selectedTime = "";
    String imagePath = "";
    final int PICK_IMAGE_REQUEST = 1;

    MedicationDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_medication);

        nameEditText = findViewById(R.id.medication_name);
        typeEditText = findViewById(R.id.medication_type);
        freqEditText = findViewById(R.id.medication_frequency);
        doseEditText = findViewById(R.id.medication_dosage);
        timeButton = findViewById(R.id.time_button);
        saveButton = findViewById(R.id.save_medication_button);
        photoImageView = findViewById(R.id.medication_photo);
        backArrow = findViewById(R.id.back_arrow_button);

        dbHelper = new MedicationDatabaseHelper(this);

        // Retour à MedicationPage
        backArrow.setOnClickListener(view -> {
            startActivity(new Intent(AddMedicationActivity.this, MedicationPageActivity.class));
            finish();
        });

        // Choisir l'heure
        timeButton.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    AddMedicationActivity.this,
                    (view, hourOfDay, minute1) -> {
                        selectedTime = hourOfDay + ":" + minute1;
                        timeButton.setText("Time: " + selectedTime);
                    },
                    hour, minute, true);
            timePickerDialog.show();
        });

        // Sélection d'image
        photoImageView.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        });

        // Enregistrer dans SQLite
        saveButton.setOnClickListener(view -> {
            String name = nameEditText.getText().toString();
            String type = typeEditText.getText().toString();
            String frequency = freqEditText.getText().toString();
            String dosage = doseEditText.getText().toString();

            dbHelper.insertMedication(name, type, frequency, dosage, selectedTime, imagePath);
            Toast.makeText(this, "Medication saved!", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(AddMedicationActivity.this, MedicationPageActivity.class));
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            imagePath = imageUri.toString();  // On sauvegarde l’URI comme chaîne de caractères

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                photoImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

