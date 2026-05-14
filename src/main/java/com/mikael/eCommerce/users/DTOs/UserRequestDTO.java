package com.mikael.eCommerce.users.DTOs;

// handle validation from front end:
// (ensure username not blank, etc.)
public record UserRequestDTO(String username, String password) {
}
