package com.mikael.eCommerce.users;

import com.mikael.eCommerce.users.DTOs.UserResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseDTO toDTO(UserEntity user){
        return new UserResponseDTO(user.getUsername(), user.getEmail());
    }
}
