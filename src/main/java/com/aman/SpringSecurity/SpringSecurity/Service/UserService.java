package com.aman.SpringSecurity.SpringSecurity.Service;

import com.aman.SpringSecurity.SpringSecurity.DTO.SignUpDTO;
import com.aman.SpringSecurity.SpringSecurity.DTO.UserDTO;
import com.aman.SpringSecurity.SpringSecurity.Entity.Users;
import com.aman.SpringSecurity.SpringSecurity.Exceptions.ResourcesNotFoundException;
import com.aman.SpringSecurity.SpringSecurity.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


public interface UserService {
    public UserDTO getUserById(Long userId);
    public UserDTO getUserByEmail(String email);
    public UserDTO save(UserDTO newUser);
    public UserDTO signUp(SignUpDTO signUpDto);
}
