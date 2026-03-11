package com.mukhtar.saas.saas.service;

import com.mukhtar.saas.saas.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mukhtar.saas.saas.entity.*;
import com.mukhtar.saas.saas.repository.*;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(String email,
                           String rawPassword,
                           String role,
                           Long adminUserId) {

        // ✅ Check if email already exists
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        // ✅ Get admin
        User admin = userRepository.findById(adminUserId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        // ✅ Create new user
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setCompany(admin.getCompany()); // Same company
        user.setRole(Role.USER);
        user.setEnabled(true); // Recommended

        return userRepository.save(user);
    }
}