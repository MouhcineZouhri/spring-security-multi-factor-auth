package com.example.springsecurityexample.security.authentications;

public class AccessTokenAuthentication  extends AbstractTokenAuthentication{

    public AccessTokenAuthentication(String token) {
        super(token);
    }
}
