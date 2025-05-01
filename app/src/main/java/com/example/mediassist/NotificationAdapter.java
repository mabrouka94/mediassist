package com.example.mediassist;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.io.File;
import java.time.Instant;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    List<NotificationItem> notificationList;
    private final OnNotificationActionListener listener;

    public interface OnNotificationActionListener {
        void onCompleteClick(int position);
        void onSkipClick(int position);
        void onDeleteClick(int position);
    }

    public NotificationAdapter(OnNotificationActionListener listener, List<NotificationItem> notificationList) {
        this.listener = listener;
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NotificationItem item = notificationList.get(position);

        // Charger l'image avec Glide
        if (item.getImagePath() != null && !item.getImagePath().isEmpty()) {
            Instant Glide;
            Glide.with(holder.itemView.getContext())
                    .load(new File(item.getImagePath()))
                    .placeholder(R.drawable.ic_medication_default)
                    .into(holder.medicationImage);
        }

        holder.medicationName.setText(item.getMedicationName());
        holder.timeText.setText(item.getTime());
        holder.messageText.setText(item.getMessage());

        // Marquage lu/non lu
        holder.itemView.setBackgroundColor(
                item.isRead() ? Color.WHITE :
                        holder.itemView.getContext().getResources().getColor(R.color.unread_notification_bg));

        // Gestion des boutons pour les médicaments
        if (item.isMedication()) {
            holder.completeButton.setVisibility(View.VISIBLE);
            holder.skipButton.setVisibility(View.VISIBLE);

            holder.completeButton.setOnClickListener(v -> {
                listener.onCompleteClick(position);
            });

            holder.skipButton.setOnClickListener(v -> {
                listener.onSkipClick(position);
            });
        } else {
            holder.completeButton.setVisibility(View.GONE);
            holder.skipButton.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public void updateList(List<NotificationItem> newList) {
        notificationList = newList;
        notifyDataSetChanged();
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        ImageView medicationImage;
        TextView medicationName, timeText, messageText;
        Button completeButton, skipButton;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            medicationImage = itemView.findViewById(R.id.medication_image);
            medicationName = itemView.findViewById(R.id.medication_name);
            timeText = itemView.findViewById(R.id.notification_time);
            messageText = itemView.findViewById(R.id.notification_message);
            completeButton = itemView.findViewById(R.id.complete_button);
            skipButton = itemView.findViewById(R.id.skip_button);
        }
    }
}