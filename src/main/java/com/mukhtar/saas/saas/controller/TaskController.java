package com.mukhtar.saas.saas.controller;

import com.mukhtar.saas.saas.dto.TaskRequest;
import com.mukhtar.saas.saas.entity.Task;
import com.mukhtar.saas.saas.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // ✅ CREATE TASK (ADMIN ONLY)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Task createTask(@RequestBody TaskRequest request,
                           Authentication authentication) {

        return taskService.createTask(request, authentication);
    }

    // ✅ ROLE-BASED VIEW
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<Task> getTasks(Authentication authentication) {

        return taskService.getTasks(authentication);
    }

    // ✅ SAFE DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteTask(@PathVariable Long id,
                           Authentication authentication) {

        taskService.deleteTask(id, authentication);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public Task updateTask(@PathVariable Long id,
                           @RequestBody TaskRequest request,
                           Authentication authentication) {

        return taskService.updateTask(id, request, authentication);
    }
}