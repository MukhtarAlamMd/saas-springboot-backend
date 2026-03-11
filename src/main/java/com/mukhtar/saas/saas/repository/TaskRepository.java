package com.mukhtar.saas.saas.repository;

import java.time.LocalDate;
import java.util.List;

import com.mukhtar.saas.saas.entity.Task;
import com.mukhtar.saas.saas.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByCompanyId(Long companyId);

    List<Task> findByAssignedToId(Long userId);
    Long countByCompanyId(Long companyId);

    Long countByCompanyIdAndStatus(Long companyId, TaskStatus status);

    Long countByCompanyIdAndDueDateBefore(Long companyId, LocalDate date);

}