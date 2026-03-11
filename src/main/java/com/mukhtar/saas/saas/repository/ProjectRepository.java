package com.mukhtar.saas.saas.repository;

import com.mukhtar.saas.saas.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByCompanyId(Long companyId);
    Long countByCompanyId(Long companyId);

}