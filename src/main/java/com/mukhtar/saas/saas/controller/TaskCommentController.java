package com.mukhtar.saas.saas.controller;

import com.mukhtar.saas.saas.dto.CommentRequest;
import com.mukhtar.saas.saas.entity.TaskComment;
import com.mukhtar.saas.saas.service.TaskCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class TaskCommentController {

    private final TaskCommentService commentService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public TaskComment addComment(@RequestBody CommentRequest request,
                                  Authentication authentication) {

        return commentService.addComment(request, authentication);
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<TaskComment> getComments(@PathVariable Long taskId) {
        return commentService.getComments(taskId);
    }
}