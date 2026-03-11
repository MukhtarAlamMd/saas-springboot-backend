package com.mukhtar.saas.saas.controller;

import com.mukhtar.saas.saas.dto.ProjectRequest;
import com.mukhtar.saas.saas.entity.Project;
import com.mukhtar.saas.saas.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Project createProject(@RequestBody ProjectRequest request,
                                 Authentication authentication) {

        return projectService.createProject(request, authentication);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<Project> getProjects(Authentication authentication) {

        return projectService.getProjects(authentication);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProject(@PathVariable Long id,
                              Authentication authentication) {

        projectService.deleteProject(id, authentication);
    }
}