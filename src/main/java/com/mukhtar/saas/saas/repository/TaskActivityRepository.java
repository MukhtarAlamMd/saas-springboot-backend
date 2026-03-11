package com.mukhtar.saas.saas.repository;

import com.mukhtar.saas.saas.entity.TaskActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskActivityRepository extends JpaRepository<TaskActivity, Long> {

    List<TaskActivity> findByTaskIdOrderByCreatedAtDesc(Long taskId);
    List<TaskActivity> findByTaskId(Long taskId);

}