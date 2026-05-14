package com.mikael.eCommerce.errors;

// use for regular http errors thrown with ResponseStatusException
// or AuthenticationException
public record CustomErrorResponse(int status, String message){

}