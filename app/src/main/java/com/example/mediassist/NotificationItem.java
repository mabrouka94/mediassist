package com.example.mediassist;

public class NotificationItem {
    private int id;
    private String time;
    private String medicationName;
    private String message;
    private String imagePath;
    private boolean isRead;
    private boolean isMedication;

    public NotificationItem(int id, String time, String medicationName, String message,
                            String imagePath, boolean isRead, boolean isMedication) {
        this.id = id;
        this.time = time;
        this.medicationName = medicationName;
        this.message = message;
        this.imagePath = imagePath;
        this.isRead = isRead;
        this.isMedication = isMedication;
    }

    // Getters
    public int getId() { return id; }
    public String getTime() { return time; }
    public String getMedicationName() { return medicationName; }
    public String getMessage() { return message; }
    public String getImagePath() { return imagePath; }
    public boolean isRead() { return isRead; }
    public boolean isMedication() { return isMedication; }

    // Setters
    public void setRead(boolean read) { isRead = read; }
}