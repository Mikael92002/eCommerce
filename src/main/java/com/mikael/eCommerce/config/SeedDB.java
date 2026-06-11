package com.mikael.eCommerce.config;

import com.mikael.eCommerce.enums.RoleEnum;
import com.mikael.eCommerce.users.UserEntity;
import com.mikael.eCommerce.users.UserMapper;
import com.mikael.eCommerce.users.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SeedDB implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SeedDB(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if(!userRepository.findByUsername("administrator").isPresent()){
            UserEntity admin = new UserEntity();
            admin.setUsername("administrator");
            admin.setRole(RoleEnum.ADMIN);
            admin.setEmail("mikael92002@gmail.com");
            admin.setPassword(passwordEncoder.encode("password"));

            UserEntity dbAdmin = userRepository.save(admin);

            System.out.println("DB seeded: Admin successfully created");
        }
    }
}
