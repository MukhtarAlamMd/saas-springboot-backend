package com.mukhtar.saas.saas.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class TaskRequest {

    private String title;
    private String description;
    private String priority;        // LOW, MEDIUM, HIGH
    private String status;          // TODO, IN_PROGRESS, DONE (optional)
    private LocalDate dueDate;
    private Long assignedUserId;
}