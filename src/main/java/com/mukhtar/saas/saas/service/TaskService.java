package com.mukhtar.saas.saas.service;
import com.mukhtar.saas.saas.dto.TaskRequest;
import com.mukhtar.saas.saas.entity.*;
import com.mukhtar.saas.saas.repository.TaskRepository;
import com.mukhtar.saas.saas.repository.UserRepository;
import com.mukhtar.saas.saas.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskActivityService activityService;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    // ✅ Get current user from JWT
    private User getCurrentUser(Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        return userRepository.findById(userDetails.id())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ✅ CREATE TASK
    public Task createTask(TaskRequest request, Authentication authentication) {

        User admin = getCurrentUser(authentication);

        if (admin.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only ADMIN can create tasks");
        }

        User assignedUser = userRepository.findById(request.getAssignedUserId())
                .orElseThrow(() -> new RuntimeException("Assigned user not found"));

        // 🔐 Multi-tenant protection
        if (!assignedUser.getCompany().getId()
                .equals(admin.getCompany().getId())) {
            throw new RuntimeException("Cannot assign task outside your company");
        }

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());

        if (request.getPriority() != null) {
            task.setPriority(TaskPriority.valueOf(
                    request.getPriority().toUpperCase()));
        }

        if (request.getStatus() != null) {
            task.setStatus(TaskStatus.valueOf(
                    request.getStatus().toUpperCase()));
        } else {
            task.setStatus(TaskStatus.TODO); // default
        }

        task.setCompany(admin.getCompany());
        task.setAssignedTo(assignedUser);
        task.setCreatedBy(admin);

        Task savedTask = taskRepository.save(task);

        // ✅ Log activity
        activityService.log(
                savedTask,
                admin,
                "TASK_CREATED",
                "Task created and assigned to " + assignedUser.getEmail()
        );

        return savedTask;
    }

    // ✅ GET TASKS
    public List<Task> getTasks(Authentication authentication) {

        User user = getCurrentUser(authentication);

        if (user.getRole() == Role.ADMIN) {
            return taskRepository.findByCompanyId(user.getCompany().getId());
        }

        return taskRepository.findByAssignedToId(user.getId());
    }

    // ✅ DELETE TASK
    public void deleteTask(Long taskId, Authentication authentication) {

        User user = getCurrentUser(authentication);

        if (user.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only ADMIN can delete tasks");
        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getCompany().getId()
                .equals(user.getCompany().getId())) {
            throw new RuntimeException("Cannot delete task outside your company");
        }

        taskRepository.delete(task);

        // ✅ Log activity
        activityService.log(
                task,
                user,
                "TASK_DELETED",
                "Task deleted by admin"
        );
    }

    // ✅ UPDATE TASK
    public Task updateTask(Long taskId,
                           TaskRequest request,
                           Authentication authentication) {

        User currentUser = getCurrentUser(authentication);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // 🔐 Multi-tenant protection
        if (!task.getCompany().getId()
                .equals(currentUser.getCompany().getId())) {
            throw new RuntimeException("Access denied: different company");
        }

        // 👤 USER restrictions
        if (currentUser.getRole() == Role.USER) {

            if (task.getAssignedTo() == null ||
                    !task.getAssignedTo().getId()
                            .equals(currentUser.getId())) {
                throw new RuntimeException("You can only update your own tasks");
            }

            if (request.getStatus() != null) {

                task.setStatus(
                        TaskStatus.valueOf(request.getStatus().toUpperCase()));

                activityService.log(
                        task,
                        currentUser,
                        "STATUS_CHANGED",
                        "Status changed to " + request.getStatus()
                );
            }

            return taskRepository.save(task);
        }

        // 👑 ADMIN update

        if (request.getTitle() != null)
            task.setTitle(request.getTitle());

        if (request.getDescription() != null)
            task.setDescription(request.getDescription());

        if (request.getPriority() != null)
            task.setPriority(
                    TaskPriority.valueOf(request.getPriority().toUpperCase()));

        if (request.getStatus() != null)
            task.setStatus(
                    TaskStatus.valueOf(request.getStatus().toUpperCase()));

        if (request.getDueDate() != null)
            task.setDueDate(request.getDueDate());

        if (request.getAssignedUserId() != null) {

            User assignedUser = userRepository
                    .findById(request.getAssignedUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!assignedUser.getCompany().getId()
                    .equals(currentUser.getCompany().getId())) {
                throw new RuntimeException("Cannot assign outside your company");
            }

            task.setAssignedTo(assignedUser);
        }

        Task updatedTask = taskRepository.save(task);

        activityService.log(
                updatedTask,
                currentUser,
                "TASK_UPDATED",
                "Task updated by admin"
        );

        return updatedTask;
    }
}