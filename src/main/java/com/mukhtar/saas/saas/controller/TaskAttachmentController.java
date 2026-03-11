package com.mukhtar.saas.saas.controller;

import com.mukhtar.saas.saas.entity.TaskAttachment;
import com.mukhtar.saas.saas.service.TaskAttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/attachments")
@RequiredArgsConstructor
public class TaskAttachmentController {

    private final TaskAttachmentService attachmentService;

    // Upload file
    @PostMapping("/{taskId}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public TaskAttachment uploadFile(@PathVariable Long taskId,
                                     @RequestParam MultipartFile file,
                                     Authentication authentication) throws Exception {

        return attachmentService.upload(taskId, file, authentication);
    }

    // Get attachments
    @GetMapping("/{taskId}")
    public List<TaskAttachment> getAttachments(@PathVariable Long taskId) {
        return attachmentService.getAttachments(taskId);
    }
}