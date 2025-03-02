package com.aman.SpringSecurity.SpringSecurity.Controllers;

import com.aman.SpringSecurity.SpringSecurity.DTO.LoginDTO;
import com.aman.SpringSecurity.SpringSecurity.DTO.LoginResponseDTO;
import com.aman.SpringSecurity.SpringSecurity.DTO.SignUpDTO;
import com.aman.SpringSecurity.SpringSecurity.DTO.UserDTO;
import com.aman.SpringSecurity.SpringSecurity.Service.AuthService;
import com.aman.SpringSecurity.SpringSecurity.Service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {


    private final UserService userService;
    private final AuthService authService;

    @Value("${deploy.env}")
    private String deployEnv;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDto) {
        UserDTO userDto = userService.signUp(signUpDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDto,HttpServletRequest request,
                                        HttpServletResponse response) {
        LoginResponseDTO loginResponseDTO = authService.login(loginDto);

        Cookie cookie = new Cookie("refreshToken", loginResponseDTO.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deployEnv));
        response.addCookie(cookie);

        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(HttpServletRequest request) {
        String refreshToken = Arrays.stream(request.getCookies()).
                filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found inside the Cookies"));
        LoginResponseDTO loginResponseDto = authService.refreshToken(refreshToken);

        return ResponseEntity.ok(loginResponseDto);
    }
}
