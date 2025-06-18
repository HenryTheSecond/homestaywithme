package com.homestaywithme.app.domain.user.usecase;

import com.homestaywithme.app.application.auth.AppUserDetails;
import com.homestaywithme.app.application.auth.JwtUtil;
import com.homestaywithme.app.application.dto.response.Response;
import com.homestaywithme.app.application.service.ResponseService;
import com.homestaywithme.app.domain.shared.constant.ResponseCode;
import com.homestaywithme.app.domain.user.repository.UserRepository;
import com.homestaywithme.app.domain.user.usecase.login.dto.request.LoginRequest;
import com.homestaywithme.app.domain.user.usecase.login.dto.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final ResponseService responseService;

    @Autowired
    public LoginUseCase(UserRepository userRepository,
                        PasswordEncoder passwordEncoder,
                        JwtUtil jwtUtil,
                        ResponseService responseService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.responseService = responseService;
    }

    public Response loginUser(LoginRequest request) {
        var username = request.getUsername();
        var optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isEmpty()) {
            return responseService.response(ResponseCode.NOT_FOUND, "User not found");
        }
        var user = optionalUser.get();

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return responseService.response(ResponseCode.NOT_FOUND, "User not found");
        }

        var accessToken = jwtUtil.generateToken(new AppUserDetails(user.getId(), user.getUsername(), user.getPassword()));
        return responseService.responseSuccessWithPayload(
                LoginResponse
                        .builder()
                        .accessToken(accessToken)
                        .build()
        );
    }
}
