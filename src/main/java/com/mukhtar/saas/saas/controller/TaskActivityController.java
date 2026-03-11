package com.mukhtar.saas.saas.controller;

import com.mukhtar.saas.saas.entity.TaskActivity;
import com.mukhtar.saas.saas.service.TaskActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task-activity")
@RequiredArgsConstructor
public class TaskActivityController {

    private final TaskActivityService activityService;

    @GetMapping("/{taskId}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<TaskActivity> getActivity(@PathVariable Long taskId) {

        return activityService.getTaskActivities(taskId);
    }
}