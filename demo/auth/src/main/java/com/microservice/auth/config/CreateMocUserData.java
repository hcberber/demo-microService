package com.microservice.auth.config;

import com.microservice.auth.model.Permission;
import com.microservice.auth.model.Role;
import com.microservice.auth.model.User;
import com.microservice.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class CreateMocUserData {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner seedDefaultUser() {
        return args -> {
            if (userRepository.existsByUsername("cberber")) return;

            Role admin = new Role();
            admin.setName("ADMIN");

            Role user = new Role();
            user.setName("USER");

            User u = new User();
            u.setUsername("cberber");
            u.setPasswordHash(passwordEncoder.encode("1234"));
            u.setIsActive(true);
            u.setRoles(new HashSet<>(Set.of(admin, user)));

            Permission read = new Permission();
            read.setName("READ");
            Permission write = new Permission();
            write.setName("WRITE");

            u.getRoles().forEach(r -> r.setPermissions(new HashSet<>(Set.of(read, write))));

            userRepository.save(u);
        };
    }
}
