package demo.booking.controllers;

import demo.booking.exception.BaseException;
import demo.booking.projector.dto.UserDto;
import demo.booking.projector.request.AuthRequest;
import demo.booking.projector.response.AuthResponse;
import demo.booking.projector.response.BaseResponse;
import demo.booking.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("register")

    public ResponseEntity<BaseResponse<UserDto>> register(@RequestBody @Valid AuthRequest request) throws BaseException {
        UserDto userDto = userService.register(request.getEmail(), request.getPassword());
        return BaseResponse.buildCreatedResponse(userDto);
    }

    @PostMapping("login")
    public ResponseEntity<BaseResponse<AuthResponse>> login(@RequestBody @Valid AuthRequest request) throws BaseException {
        AuthResponse authResponse = userService.login(request.getEmail(), request.getPassword());
        return BaseResponse.buildCreatedResponse(authResponse);
    }
}
