package com.mukhtar.saas.saas.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardStats {

    private Long totalProjects;
    private Long totalTasks;
    private Long completedTasks;
    private Long pendingTasks;
    private Long overdueTasks;
    private Long totalUsers;

}