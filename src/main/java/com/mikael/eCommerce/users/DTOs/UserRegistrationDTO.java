package com.mikael.eCommerce.users.DTOs;

import jakarta.validation.constraints.*;

// from front end to backend during registration:
public record UserRegistrationDTO(
        @NotBlank(message = "Username cannot be blank")
        @Size(min = 8, max = 64, message = "Username must be between 8 and 64 characters")
        String username,
        @Email
        // doing NotNull: if email is null no db call gets made
        // for nullable = false, db call gets made, then fails
        @NotBlank(message = "Email cannot be blank")
        @Size(max = 250, message = "Email must be less than 250 characters")
        String email,
        @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
        @NotBlank(message = "Password cannot be blank")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$",
                message = "Password must contain at least one digit, one lowercase, and one uppercase letter")
        String password) {
}
