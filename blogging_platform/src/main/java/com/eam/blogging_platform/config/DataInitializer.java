package com.eam.blogging_platform.config;

import com.eam.blogging_platform.entity.Role;
import com.eam.blogging_platform.entity.Status;
import com.eam.blogging_platform.entity.User;
import com.eam.blogging_platform.repository.RoleRepository;
import com.eam.blogging_platform.repository.StatusRepository;
import com.eam.blogging_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // If "DRAFT" status does not exist, create it
        Optional<Status> draftStatus = statusRepository.findByStatus("DRAFT");
        if (draftStatus.isEmpty()) {
            Status draft = new Status();
            draft.setStatus("DRAFT");
            statusRepository.save(draft);
        }

        // If "PUBLISHED" status does not exist, create it
        Optional<Status> publishedStatus = statusRepository.findByStatus("PUBLISHED");
        if (publishedStatus.isEmpty()) {
            Status published = new Status();
            published.setStatus("PUBLISHED");
            statusRepository.save(published);
        }

        // If "BANNED" status does not exist, create it
        Optional<Status> bannedStatus = statusRepository.findByStatus("BANNED");
        if (bannedStatus.isEmpty()) {
            Status banned = new Status();
            banned.setStatus("BANNED");
            statusRepository.save(banned);
        }

        // If "admin role" does not exist, create it
        Optional<Role> adminRole = roleRepository.findByRole("admin");
        if (adminRole.isEmpty()) {
            Role admin = new Role();
            admin.setRole("admin");
            admin.setDescription("admin");
            roleRepository.save(admin);
        }

        // If "author role" does not exist, create it
        Optional<Role> authorRole = roleRepository.findByRole("author");
        if (authorRole.isEmpty()) {
            Role author = new Role();
            author.setRole("author");
            author.setDescription("author");
            roleRepository.save(author);
        }

        // If admin by default does not exist, create it
        Optional<User> adminUser = userRepository.findByUsername("SebasDefaultAdmin");
        if (adminUser.isEmpty()) {
            User defaultAdmin = new User();
            defaultAdmin.setUsername("SebasDefaultAdmin");
            defaultAdmin.setEmail("sda@gmail.com");
            defaultAdmin.setPassword(passwordEncoder.encode("Sdasda1#")); // Importante encriptar la contraseña
            defaultAdmin.setRole(roleRepository.findByRole("admin").get()); // Obtiene el rol insertado arriba
            defaultAdmin.setCreationDate(LocalDateTime.now());
            userRepository.save(defaultAdmin);
        }
    }
}
