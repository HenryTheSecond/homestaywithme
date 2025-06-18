package com.homestaywithme.app.domain.user.usecase.login.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
