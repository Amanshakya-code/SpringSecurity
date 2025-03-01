package com.aman.SpringSecurity.SpringSecurity.Service;

import com.aman.SpringSecurity.SpringSecurity.DTO.SignUpDTO;
import com.aman.SpringSecurity.SpringSecurity.DTO.UserDTO;
import com.aman.SpringSecurity.SpringSecurity.Entity.PostEntity;
import com.aman.SpringSecurity.SpringSecurity.Entity.Users;
import com.aman.SpringSecurity.SpringSecurity.Exceptions.ResourcesNotFoundException;
import com.aman.SpringSecurity.SpringSecurity.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements  UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO getUserById(Long userId){
        Users user =  userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourcesNotFoundException("User with id "+userId +" not found"));

        return modelMapper.map(user,UserDTO.class);
    }

    @Override
    public UserDTO getUserByEmail(String email){
        Users user =  userRepository.findByEmail(email).orElse(null);
        if(user == null) throw new ResourcesNotFoundException("User does not exist with this email id");
        return modelMapper.map(user,UserDTO.class);
    }

    @Override
    public UserDTO save(UserDTO newUser) {
        Users userEntity = modelMapper.map(newUser, Users.class);
        return modelMapper.map(userEntity,UserDTO.class);
    }

    public UserDTO signUp(SignUpDTO signUpDto) {
        Optional<Users> user = userRepository.findByEmail(signUpDto.getEmail());
        if(user.isPresent()) {
            throw new BadCredentialsException("User with email already exits "+ signUpDto.getEmail());
        }

        Users toBeCreatedUser = modelMapper.map(signUpDto, Users.class);
        toBeCreatedUser.setPassword(passwordEncoder.encode(toBeCreatedUser.getPassword()));

        Users savedUser = userRepository.save(toBeCreatedUser);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException("User with email "+ username +" not found"));
    }
}
