package com.mukhtar.saas.saas.security;
import com.mukhtar.saas.saas.entity.User;
import com.mukhtar.saas.saas.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new CustomUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(), // 🔥 IMPORTANT
                user.getRole().name(),
                user.isEnabled(),
                user.getCompany().getId()

        );
    }
}