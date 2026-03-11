package com.mukhtar.saas.saas.service;

import com.mukhtar.saas.saas.dto.CommentRequest;
import com.mukhtar.saas.saas.entity.*;
import com.mukhtar.saas.saas.repository.*;
import com.mukhtar.saas.saas.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskCommentService {

    private final TaskRepository taskRepository;
    private final TaskCommentRepository commentRepository;
    private final UserRepository userRepository;

    public TaskComment addComment(CommentRequest request, Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        User user = userRepository.findById(userDetails.id())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = taskRepository.findById(request.getTaskId())
                .orElseThrow(() -> new RuntimeException("Task not found"));

        TaskComment comment = TaskComment.builder()
                .comment(request.getComment())
                .createdAt(LocalDateTime.now())
                .task(task)
                .user(user)
                .build();

        return commentRepository.save(comment);
    }

    public List<TaskComment> getComments(Long taskId) {
        return commentRepository.findByTaskId(taskId);
    }
}