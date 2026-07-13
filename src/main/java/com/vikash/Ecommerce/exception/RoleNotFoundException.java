package com.vikash.Ecommerce.exception;

public class RoleNotFoundException extends ResourceNotFoundException {

    public RoleNotFoundException(String message) {
        super(message);
    }
}