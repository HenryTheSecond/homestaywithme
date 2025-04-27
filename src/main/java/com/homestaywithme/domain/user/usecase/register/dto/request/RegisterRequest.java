package com.homestaywithme.domain.user.usecase.register.dto.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String fullname;
}
