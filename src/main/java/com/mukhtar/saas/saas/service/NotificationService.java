package com.mukhtar.saas.saas.service;

import com.mukhtar.saas.saas.entity.Notification;
import com.mukhtar.saas.saas.entity.User;
import com.mukhtar.saas.saas.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    // Create notification
    public void notify(User user, String title, String message) {

        Notification notification = Notification.builder()
                .user(user)
                .title(title)
                .message(message)
                .build();

        notificationRepository.save(notification);
    }

    // Get user notifications
    public List<Notification> getUserNotifications(Long userId) {

        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    // Mark notification as read
    public void markAsRead(Long id) {

        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notification.setRead(true);

        notificationRepository.save(notification);
    }
}