package com.homestaywithme.domain.user.usecase.register;

import com.homestaywithme.application.auth.AppUserDetails;
import com.homestaywithme.application.auth.JwtUtil;
import com.homestaywithme.application.dto.response.Response;
import com.homestaywithme.application.service.ResponseService;
import com.homestaywithme.domain.shared.constant.ResponseCode;
import com.homestaywithme.domain.user.entity.User;
import com.homestaywithme.domain.user.repository.UserRepository;
import com.homestaywithme.domain.user.usecase.register.dto.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtl;
    private final ResponseService responseService;

    @Autowired
    public RegisterUseCase(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil,
                           ResponseService responseService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtl = jwtUtil;
        this.responseService = responseService;
    }

    public Response registerUser(RegisterRequest request) {
        if(userRepository.findByUsername(request.getUsername()).isPresent()) {
            responseService.response(ResponseCode.BAD_REQUEST, "User already existed");
        }
        var user = User
                .builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .fullname(request.getFullname())
                .status(0)
                .type(0)
                .build();

        userRepository.save(user);
        var token = jwtUtl.generateToken(new AppUserDetails(user.getId(), user.getUsername(), ""));
        return responseService.responseSuccessWithPayload(token);
    }
}
