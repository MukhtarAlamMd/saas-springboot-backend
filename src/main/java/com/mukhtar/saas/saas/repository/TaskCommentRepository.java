package com.mukhtar.saas.saas.repository;

import com.mukhtar.saas.saas.entity.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {

    List<TaskComment> findByTaskId(Long taskId);
}