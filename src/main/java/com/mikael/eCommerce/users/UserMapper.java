package com.mikael.eCommerce.users;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDTO(UserEntity user){
        return new UserDTO(user.getUsername(), user.getEmail());
    }
}
