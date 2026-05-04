package org.dawn.backend.config.setup;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dawn.backend.config.web.AppConfig;
import org.dawn.backend.config.security.hashing.PasswordEncoder;
import org.dawn.backend.constant.system.Message;
import org.dawn.backend.constant.auth.URole;
import org.dawn.backend.entity.Role;
import org.dawn.backend.entity.User;
import org.dawn.backend.exception.wrapper.ResourceNotFoundException;
import org.dawn.backend.repository.auth.RoleRepository;
import org.dawn.backend.repository.auth.UserRepository;

@Slf4j
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;


    public void run() {
        try {
            createAdminAccountIfNotExist();
            createStockAccountIfNotExist();
            createSaleAccountIfNotExist();
        } catch (Exception e) {
            log.error("Error initializing setup account", e);
        }
    }

    //    Note: This just for demo, don't use this in production
    private void createAdminAccountIfNotExist() {
        String username = AppConfig.get("setup.admin.username");
        String password = AppConfig.get("setup.admin.password");
        if (userRepository.existsByUserName(username)) {
            log.info("Admin account '{}' already exists. Skipping initialization.", username);
            return;
        }

        Role role = roleRepository
                .findByName(URole.ADMIN)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Message.Exception.ROLE_NOT_FOUND));

        User user = User
                .builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .isDeleted(false)
                .build();

        userRepository.save(user);

        log.info("========================================");
        log.info("   DEMO ADMIN ACCOUNT CREATED");
        log.info("   Username: {}", username);
        log.info("   Password: {}", password);
        log.info("========================================");
    }

    private void createStockAccountIfNotExist() {
        String username = AppConfig.get("setup.stock.username");
        String password = AppConfig.get("setup.stock.password");
        if (userRepository.existsByUserName(username)) {
            log.info("Stock account '{}' already exists. Skipping initialization.", username);
            return;
        }

        Role role = roleRepository
                .findByName(URole.STOCK)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Message.Exception.ROLE_NOT_FOUND));

        User user = User
                .builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .isDeleted(false)
                .build();

        userRepository.save(user);

        log.info("========================================");
        log.info("   DEMO STOCK ACCOUNT CREATED");
        log.info("   Username: {}", username);
        log.info("   Password: {}", password);
        log.info("========================================");
    }


    private void createSaleAccountIfNotExist() {
        String username = AppConfig.get("setup.sale.username");
        String password = AppConfig.get("setup.sale.password");
        if (userRepository.existsByUserName(username)) {
            log.info("sale account '{}' already exists. Skipping initialization.", username);
            return;
        }

        Role role = roleRepository
                .findByName(URole.SALES)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Message.Exception.ROLE_NOT_FOUND));

        User user = User
                .builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .isDeleted(false)
                .build();

        userRepository.save(user);

        log.info("========================================");
        log.info("   DEMO SALE ACCOUNT CREATED");
        log.info("   Username: {}", username);
        log.info("   Password: {}", password);
        log.info("========================================");
    }
}
