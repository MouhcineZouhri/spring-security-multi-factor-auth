package com.example.springsecurityexample.security.authentications;


public class RefreshTokenAuthentication extends AbstractTokenAuthentication {

    public RefreshTokenAuthentication(String refreshToken) {
        super(refreshToken);
    }

}
