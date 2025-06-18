package com.homestaywithme.app.domain.user.usecase.register;

import com.homestaywithme.app.application.auth.AppUserDetails;
import com.homestaywithme.app.application.auth.JwtUtil;
import com.homestaywithme.app.application.dto.response.Response;
import com.homestaywithme.app.application.service.ResponseService;
import com.homestaywithme.app.domain.shared.constant.ResponseCode;
import com.homestaywithme.app.domain.user.entity.User;
import com.homestaywithme.app.domain.user.repository.UserRepository;
import com.homestaywithme.app.domain.user.usecase.register.dto.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
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
