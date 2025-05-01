package com.example.mediassist;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity
        implements NotificationAdapter.OnNotificationActionListener {

    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private MedicationDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        dbHelper = new MedicationDatabaseHelper(this);
        recyclerView = findViewById(R.id.notifications_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadNotifications();
    }

    private void loadNotifications() {
        List<NotificationItem> notifications = dbHelper.getAllNotifications();
        adapter = new NotificationAdapter(this, notifications);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCompleteClick(int position) {
        NotificationItem item = adapter.notificationList.get(position);
        dbHelper.markAsRead(item.getId());
        loadNotifications();
    }

    @Override
    public void onSkipClick(int position) {
        NotificationItem item = adapter.notificationList.get(position);
        dbHelper.markAsRead(item.getId());
        // Logique supplémentaire pour le skip
        loadNotifications();
    }

    @Override
    public void onDeleteClick(int position) {
        NotificationItem item = adapter.notificationList.get(position);
        dbHelper.deleteNotification(item.getId());
        loadNotifications();
    }
}