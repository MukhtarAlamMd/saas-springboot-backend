package com.mukhtar.saas.saas.service;

import com.mukhtar.saas.saas.entity.*;
import com.mukhtar.saas.saas.repository.TaskActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskActivityService {

    private final TaskActivityRepository activityRepository;

    public void log(Task task, User user, String action, String description) {

        TaskActivity activity = TaskActivity.builder()
                .task(task)
                .user(user)
                .action(action)
                .description(description)
                .createdAt(LocalDateTime.now())
                .build();
        activityRepository.save(activity);
    }

    public List<TaskActivity> getTaskActivities(Long taskId) {
        return activityRepository.findByTaskIdOrderByCreatedAtDesc(taskId);
    }
}