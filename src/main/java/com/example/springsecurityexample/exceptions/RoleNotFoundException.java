package com.example.springsecurityexample.exceptions;

public class RoleNotFoundException extends RuntimeException{

    public RoleNotFoundException(String roleName){
        super("Role with " + roleName + " Not Found");
    }
}
