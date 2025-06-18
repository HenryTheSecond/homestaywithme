package com.homestaywithme.app.api;

import com.homestaywithme.app.application.dto.response.Response;
import com.homestaywithme.app.domain.user.usecase.LoginUseCase;
import com.homestaywithme.app.domain.user.usecase.login.dto.request.LoginRequest;
import com.homestaywithme.app.domain.user.usecase.register.RegisterUseCase;
import com.homestaywithme.app.domain.user.usecase.register.dto.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final RegisterUseCase registerUseCase;
    private final LoginUseCase loginUseCase;

    @Autowired
    public AuthController(RegisterUseCase registerUseCase,
                          LoginUseCase loginUseCase) {
        this.registerUseCase = registerUseCase;
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/register")
    @PreAuthorize("permitAll()")
    public Response registerUser(@RequestBody RegisterRequest request) {
        return registerUseCase.registerUser(request);
    }

    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public Response login(@RequestBody LoginRequest request) {
        return loginUseCase.loginUser(request);
    }
}
