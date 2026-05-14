package com.mikael.eCommerce.users;

import com.mikael.eCommerce.roles.RoleEnum;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public UserDTO createUser(String username, String email, String rawPassword){
        if(userRepository.findByEmail(email).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CREATE FAILED: Email already exists");
        }
        if(userRepository.findByUsername(username).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CREATE FAILED: Username already exists");
        }
        String encoded = passwordEncoder.encode(rawPassword);
        String cleanEmail = email.toLowerCase().strip();

        UserEntity newUser = new UserEntity();
        newUser.setUsername(username);
        newUser.setRole(RoleEnum.USER);
        newUser.setEmail(cleanEmail);
        newUser.setPassword(encoded);

        UserEntity dbUser = userRepository.save(newUser);

        return this.userMapper.toDTO(dbUser);
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword){
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserEntity> getAllUsers(){
        return this.userRepository.findAll();
    }
}
