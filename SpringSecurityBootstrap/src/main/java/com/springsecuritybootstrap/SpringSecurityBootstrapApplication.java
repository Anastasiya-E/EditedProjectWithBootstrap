package com.springsecuritybootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.springsecuritybootstrap.model.Role;
import com.springsecuritybootstrap.model.User;
import com.springsecuritybootstrap.repository.RoleRepository;
import com.springsecuritybootstrap.repository.UserRepository;

import java.util.HashSet;

@SpringBootApplication
public class SpringSecurityBootstrapApplication implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SpringSecurityBootstrapApplication(RoleRepository roleRepository,
                                              UserRepository userRepository,
                                              PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityBootstrapApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Role admin = new Role("ROLE_ADMIN");
        Role user = new Role("ROLE_USER");
        roleRepository.save(admin);
        roleRepository.save(user);
        roleRepository.save(new Role("ROLE_GUEST"));

        userRepository.save(new User("Иван", "Иванов", 30, "admin@mail.com",
                passwordEncoder.encode("admin"),
                new HashSet<>() {{
                    add(admin);
                    add(user);
                }}));
        userRepository.save(new User("Петр", "Петров", 28, "user@mail.com",
                passwordEncoder.encode("user"),
                new HashSet<>() {{
                    add(user);
                }}));
    }
}
