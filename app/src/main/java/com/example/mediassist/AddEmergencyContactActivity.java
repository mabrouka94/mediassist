package com.example.mediassist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.io.IOException;

public class AddEmergencyContactActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 101;
    private EditText nameEditText, phoneEditText;
    private Button uploadImageButton, addContactButton;
    private ImageView contactImageView;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact_emergency);

        initViews();
        setupToolbar();
        setupListeners();
    }

    private void initViews() {
        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        uploadImageButton = findViewById(R.id.uploadImageButton);
        addContactButton = findViewById(R.id.addContactButton);
        contactImageView = findViewById(R.id.contactImageView);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(""); // Supprime le titre par défaut
        }
    }

    private void setupListeners() {
        uploadImageButton.setOnClickListener(v -> openImagePicker());
        addContactButton.setOnClickListener(v -> validateAndSaveContact());
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                contactImageView.setImageBitmap(bitmap);
                contactImageView.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                showToast(getString(R.string.image_load_error));
                e.printStackTrace();
            }
        }
    }

    private void validateAndSaveContact() {
        String name = nameEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();

        if (!isInputValid(name, phone)) {
            return;
        }

        saveContact(name, phone);
    }

    private boolean isInputValid(String name, String phone) {
        if (name.isEmpty()) {
            nameEditText.setError(getString(R.string.name_required));
            return false;
        }

        if (phone.isEmpty()) {
            phoneEditText.setError(getString(R.string.phone_required));
            return false;
        }

        if (!isValidPhoneNumber(phone)) {
            phoneEditText.setError(getString(R.string.invalid_phone));
            return false;
        }

        return true;
    }

    private boolean isValidPhoneNumber(String phone) {
        return phone.matches("^[0-9]{8,15}$");
    }

    private void saveContact(String name, String phone) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("contact_name", name);
        resultIntent.putExtra("contact_phone", phone);

        if (imageUri != null) {
            resultIntent.putExtra("contact_image_uri", imageUri.toString());
        }

        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}