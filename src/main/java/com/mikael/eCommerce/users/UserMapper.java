package com.mikael.eCommerce.users;

import com.mikael.eCommerce.users.DTOs.UserRequestDTO;
import com.mikael.eCommerce.users.DTOs.UserResponseDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper{
    // from backend to frontend:
    UserResponseDTO toDTO(UserEntity user);
    // from front end to backend:
}