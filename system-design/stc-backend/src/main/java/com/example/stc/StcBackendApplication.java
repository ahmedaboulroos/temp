package com.example.stc;

import com.example.stc.database.entity.Permission;
import com.example.stc.database.entity.PermissionGroup;
import com.example.stc.database.entity.PermissionLevel;
import com.example.stc.database.repository.PermissionGroupRepository;
import com.example.stc.database.repository.PermissionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StcBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(StcBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner init(PermissionRepository permissionRepository, PermissionGroupRepository permissionGroupRepository) {
        return args -> {
            PermissionGroup group1 = new PermissionGroup();
            group1.setName("admins");
            PermissionGroup savedGroup = permissionGroupRepository.save(group1);

            Permission permission1 = new Permission();
            permission1.setEmail("viewer");
            permission1.setLevel(PermissionLevel.VIEW);
            permission1.setGroup(savedGroup);
            permissionRepository.save(permission1);

            Permission permission2 = new Permission();
            permission2.setEmail("editor");
            permission2.setLevel(PermissionLevel.EDIT);
            permission2.setGroup(savedGroup);
            permissionRepository.save(permission2);
        };
    }
}
