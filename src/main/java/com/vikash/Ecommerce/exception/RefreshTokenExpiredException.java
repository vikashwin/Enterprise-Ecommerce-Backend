package com.vikash.Ecommerce.exception;

public class RefreshTokenExpiredException extends RuntimeException{

    public RefreshTokenExpiredException(String message){
        super(message);
    }
}
