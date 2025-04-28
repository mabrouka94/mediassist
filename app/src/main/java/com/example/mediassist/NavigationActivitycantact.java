package com.example.mediassist;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NavigationActivityContact extends AppCompatActivity {
    private void showAddEmergencyContactDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.add_emergency_contact, null);
        builder.setView(view);

        EditText nameEditText = view.findViewById(R.id.nameEditText);
        EditText phoneEditText = view.findViewById(R.id.phoneEditText);
        Button uploadImageButton = view.findViewById(R.id.uploadImageButton);
        Button addContactButton = view.findViewById(R.id.addContactButton);

        AlertDialog dialog = builder.create();
        dialog.show();

        uploadImageButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 100);  // 100 is the request code
        });

        addContactButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String phone = phoneEditText.getText().toString().trim();

            if (!name.isEmpty() && !phone.isEmpty()) {
                // Here, you add the new contact to your contact list
                Contact newContact = new Contact(name, phone, imageUri); // Don't forget to define imageUri
                contactList.add(newContact);  // Update your RecyclerView or list here
                dialog.dismiss();
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            // You can now use the selected image URI
        }
    }
}
