package com.mukhtar.saas.saas.service;
import com.mukhtar.saas.saas.entity.Role;
import com.mukhtar.saas.saas.dto.LoginRequest;
import com.mukhtar.saas.saas.dto.RegisterCompanyRequest;
import com.mukhtar.saas.saas.entity.Company;
import com.mukhtar.saas.saas.entity.User;
import com.mukhtar.saas.saas.repository.CompanyRepository;
import com.mukhtar.saas.saas.repository.UserRepository;
import com.mukhtar.saas.saas.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService ;
    private final AuthenticationManager authenticationManager;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerCompany(RegisterCompanyRequest request) {

        Company company = new Company();
        company.setName(request.getCompanyName());
        company.setSubscriptionType("FREE");

        companyRepository.save(company);

        User admin = new User();
        admin.setEmail(request.getEmail());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        //admin.setRole(Role.valueOf(request.getRole().toUpperCase()));
        admin.setRole(Role.ADMIN);
        admin.setEnabled(true);
        admin.setCompany(company);

        userRepository.save(admin);
    }


    public String logins(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jwtService.generateToken(
                user.getEmail(),
                user.getId(),
                user.getCompany().getId(),
                user.getRole().name()
        );
    }


}