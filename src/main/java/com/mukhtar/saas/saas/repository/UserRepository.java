package com.mukhtar.saas.saas.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.mukhtar.saas.saas.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Long countByCompanyId(Long companyId);
}