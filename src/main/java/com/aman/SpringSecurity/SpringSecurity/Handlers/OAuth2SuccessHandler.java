package com.aman.SpringSecurity.SpringSecurity.Handlers;

import com.aman.SpringSecurity.SpringSecurity.DTO.UserDTO;
import com.aman.SpringSecurity.SpringSecurity.Entity.Users;
import com.aman.SpringSecurity.SpringSecurity.Service.JWTService;
import com.aman.SpringSecurity.SpringSecurity.Service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler  extends SimpleUrlAuthenticationSuccessHandler {


    private final UserService userService;
    private final JWTService jwtService;
    private final ModelMapper modelMapper;

    @Value("${deploy.env}")
    private String deployEnv;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) token.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        UserDTO user = userService.getUserByEmail(email);

        if(user == null) {
            Users newUser = Users.builder()
                    .name(oAuth2User.getAttribute("name"))
                    .email(email).build();

            user = userService.save(modelMapper.map(newUser,UserDTO.class));
        }
        Users savedUser = modelMapper.map(user,Users.class);
        String accessToken = jwtService.generateAccessToken(savedUser);
        String refreshToken = jwtService.generateRefreshToken(savedUser);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deployEnv));
        response.addCookie(cookie);

        String frontEndUrl = "http://localhost:8080/home.html?token="+accessToken;

//        getRedirectStrategy().sendRedirect(request, response, frontEndUrl);

        response.sendRedirect(frontEndUrl);
    }
}
