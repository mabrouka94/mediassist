package com.example.mediassist;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.Calendar;

public class AddAppointmentActivity extends AppCompatActivity {

    private EditText noteEditText, dateEditText;
    private ImageView timeImageView;
    private RadioGroup categoryRadioGroup;
    private Button addCategoryButton, createEventButton;
    private LinearLayout categoryContainer;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addappointements);

        initViews();
        setupToolbar();
        setupListeners();

        calendar = Calendar.getInstance();
    }

    private void initViews() {
        noteEditText = findViewById(R.id.noteEditText);
        dateEditText = findViewById(R.id.dateEditText);
        timeImageView = findViewById(R.id.timeImageView);
        categoryRadioGroup = findViewById(R.id.categoryRadioGroup);
        addCategoryButton = findViewById(R.id.addCategoryButton);
        createEventButton = findViewById(R.id.createEventButton);
        categoryContainer = findViewById(R.id.categoryContainer);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupListeners() {
        // Date picker
        dateEditText.setOnClickListener(v -> showDatePicker());

        // Time picker
        timeImageView.setOnClickListener(v -> showTimePicker());

        // Add new category
        addCategoryButton.setOnClickListener(v -> addNewCategory());

        // Create appointment
        createEventButton.setOnClickListener(v -> createAppointment());
    }

    private void showDatePicker() {
        new DatePickerDialog(this, this::onDateSet,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void onDateSet(DatePicker view, int year, int month, int day) {
        calendar.set(year, month, day);
        dateEditText.setText(day + "/" + (month + 1) + "/" + year);
    }

    private void showTimePicker() {
        new TimePickerDialog(this, this::onTimeSet,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true)
                .show();
    }

    private void onTimeSet(TimePicker view, int hour, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        Toast.makeText(this, "Time set: " + hour + ":" + minute, Toast.LENGTH_SHORT).show();
    }

    private void addNewCategory() {
        EditText newCategory = new EditText(this);
        newCategory.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        newCategory.setHint("New category name");
        newCategory.setBackgroundResource(R.drawable.edittext_background);
        newCategory.setPadding(16, 16, 16, 16);

        categoryContainer.addView(newCategory, categoryContainer.getChildCount() - 2);
    }

    private void createAppointment() {
        String note = noteEditText.getText().toString().trim();
        String date = dateEditText.getText().toString().trim();

        if (note.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedId = categoryRadioGroup.getCheckedRadioButtonId();
        String category = "General";
        if (selectedId != -1) {
            RadioButton radioButton = findViewById(selectedId);
            category = radioButton.getText().toString();
        }

        Intent result = new Intent();
        result.putExtra("note", note);
        result.putExtra("date", date);
        result.putExtra("category", category);
        setResult(RESULT_OK, result);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}