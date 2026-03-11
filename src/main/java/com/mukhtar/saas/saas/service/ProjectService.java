package com.mukhtar.saas.saas.service;

import com.mukhtar.saas.saas.dto.ProjectRequest;
import com.mukhtar.saas.saas.entity.*;
import com.mukhtar.saas.saas.repository.ProjectRepository;
import com.mukhtar.saas.saas.repository.UserRepository;
import com.mukhtar.saas.saas.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    private User getCurrentUser(Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        return userRepository.findById(userDetails.id())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // CREATE PROJECT
    public Project createProject(ProjectRequest request,
                                 Authentication authentication) {

        User admin = getCurrentUser(authentication);

        if (admin.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only ADMIN can create projects");
        }

        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setCompany(admin.getCompany());
        project.setCreatedBy(admin);

        return projectRepository.save(project);
    }

    // GET PROJECTS
    public List<Project> getProjects(Authentication authentication) {

        User user = getCurrentUser(authentication);

        return projectRepository
                .findByCompanyId(user.getCompany().getId());
    }

    // DELETE PROJECT
    public void deleteProject(Long projectId,
                              Authentication authentication) {

        User admin = getCurrentUser(authentication);

        if (admin.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only ADMIN can delete projects");
        }

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        if (!project.getCompany().getId()
                .equals(admin.getCompany().getId())) {
            throw new RuntimeException("Access denied");
        }

        projectRepository.delete(project);
    }
}