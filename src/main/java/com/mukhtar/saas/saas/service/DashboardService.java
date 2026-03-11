package com.mukhtar.saas.saas.service;

import com.mukhtar.saas.saas.dto.DashboardStats;
import com.mukhtar.saas.saas.entity.TaskStatus;
import com.mukhtar.saas.saas.entity.User;
import com.mukhtar.saas.saas.repository.*;
import com.mukhtar.saas.saas.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public DashboardStats getStats(Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        Long companyId = userDetails.companyId();

        Long totalProjects = projectRepository.countByCompanyId(companyId);

        Long totalTasks = taskRepository.countByCompanyId(companyId);

        Long completedTasks = taskRepository.countByCompanyIdAndStatus(
                companyId, TaskStatus.DONE);

        Long pendingTasks = taskRepository.countByCompanyIdAndStatus(
                companyId, TaskStatus.TODO);

        Long overdueTasks = taskRepository.countByCompanyIdAndDueDateBefore(
                companyId, LocalDate.now());

        Long totalUsers = userRepository.countByCompanyId(companyId);

        return DashboardStats.builder()
                .totalProjects(totalProjects)
                .totalTasks(totalTasks)
                .completedTasks(completedTasks)
                .pendingTasks(pendingTasks)
                .overdueTasks(overdueTasks)
                .totalUsers(totalUsers)
                .build();
    }
}