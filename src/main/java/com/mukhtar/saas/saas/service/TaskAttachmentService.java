package com.mukhtar.saas.saas.service;

import com.mukhtar.saas.saas.entity.*;
import com.mukhtar.saas.saas.repository.*;
import com.mukhtar.saas.saas.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskAttachmentService {

    private final TaskAttachmentRepository attachmentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private final String uploadDir = "uploads/";

    private User getCurrentUser(Authentication authentication) {
        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        return userRepository.findById(userDetails.id())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Upload file
    public TaskAttachment upload(Long taskId,
                                 MultipartFile file,
                                 Authentication authentication) throws Exception {

        User user = getCurrentUser(authentication);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        Path path = Paths.get(uploadDir + file.getOriginalFilename());

        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        TaskAttachment attachment = TaskAttachment.builder()
                .task(task)
                .uploadedBy(user)
                .fileName(file.getOriginalFilename())
                .fileType(file.getContentType())
                .fileSize(file.getSize())
                .filePath(path.toString())
                .build();

        return attachmentRepository.save(attachment);
    }

    // Get attachments
    public List<TaskAttachment> getAttachments(Long taskId) {
        return attachmentRepository.findByTaskId(taskId);
    }
}