package com.example.springsecurityexample.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String username){
        super("User with " + username + " Not Found");
    }
}
