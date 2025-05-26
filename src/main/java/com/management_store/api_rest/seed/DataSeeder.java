package com.management_store.api_rest.seed;

import com.management_store.api_rest.models.*;
import com.management_store.api_rest.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(
        PermissionRepository permissionRepository,
        RoleRepository roleRepository,
        UserRepository userRepository,
        PasswordEncoder passwordEncoder) {
        return args -> {

            if (permissionRepository.findAll().isEmpty()) {
                // 🔐 Base permissions
                List<Permission> allPermissions = List.of(
                    newPermission("Ver usuarios", "user.read"),
                    newPermission("Crear usuarios", "user.create"),
                    newPermission("Ver productos", "product.read"),
                    newPermission("Crear productos", "product.create"),
                    newPermission("Ver ventas", "sales.read"),
                    newPermission("Hacer ventas", "sales.create"),
                    newPermission("Ver reportes", "report.view"),
                    newPermission("Monitorear sistema", "system.monitor"));
                permissionRepository.saveAll(allPermissions);

                // Map fas
                Map<String, Permission> permMap = new HashMap<>();
                for (Permission p : permissionRepository.findAll()) {
                    permMap.put(p.getValue(), p);
                }

                // 🛡️ Roles
                Role superadmin = new Role();
                superadmin.setName("superadmin");
                superadmin.setPermissions(new HashSet<>(permMap.values()));
                roleRepository.save(superadmin);

                Role admin = new Role();
                admin.setName("admin");
                admin.setPermissions(Set.of(
                    permMap.get("user.read"),
                    permMap.get("product.read"),
                    permMap.get("product.create"),
                    permMap.get("sales.read"),
                    permMap.get("sales.create")));
                roleRepository.save(admin);

                Role employee = new Role();
                employee.setName("employee");
                employee.setPermissions(Set.of(
                        permMap.get("sales.create")));
                roleRepository.save(employee);

                // 👤 Seeder superadmin
                if (userRepository.findByEmail("superadmin@tienda.com").isEmpty()) {
                    User superUser = new User();
                    superUser.setName("Super Admin");
                    superUser.setEmail("superadmin@tienda.com");
                    superUser.setPassword(passwordEncoder.encode("superclave123"));
                    superUser.setStatus(1);
                    superUser.setRole(superadmin);
                    userRepository.save(superUser);
                }
            }
        };
    }

    private Permission newPermission(String name, String value) {
        Permission p = new Permission();
        p.setName(name);
        p.setValue(value);
        return p;
    }
}
