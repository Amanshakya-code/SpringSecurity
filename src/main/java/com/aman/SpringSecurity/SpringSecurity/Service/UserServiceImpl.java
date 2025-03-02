package com.aman.SpringSecurity.SpringSecurity.Service;

import com.aman.SpringSecurity.SpringSecurity.DTO.SignUpDTO;
import com.aman.SpringSecurity.SpringSecurity.DTO.UserDTO;
import com.aman.SpringSecurity.SpringSecurity.Entity.Enums.Subscriptions;
import com.aman.SpringSecurity.SpringSecurity.Entity.PostEntity;
import com.aman.SpringSecurity.SpringSecurity.Entity.SessionEntity;
import com.aman.SpringSecurity.SpringSecurity.Entity.SubscriptionEntity;
import com.aman.SpringSecurity.SpringSecurity.Entity.Users;
import com.aman.SpringSecurity.SpringSecurity.Exceptions.ResourcesNotFoundException;
import com.aman.SpringSecurity.SpringSecurity.Repositories.UserRepository;
import com.aman.SpringSecurity.SpringSecurity.Utils.SessionCountMapper;
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
        if(user == null) return null;
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
        if (signUpDto.getRoles() == null || signUpDto.getRoles().isEmpty()) {
            throw new IllegalArgumentException("Roles cannot be null or empty");
        }

        Users toBeCreatedUser = modelMapper.map(signUpDto, Users.class);
        toBeCreatedUser.setPassword(passwordEncoder.encode(toBeCreatedUser.getPassword()));

        //adding the subscription to the user
        SubscriptionEntity subscriptionEntity = SubscriptionEntity.builder()
                .users(toBeCreatedUser)
                .plan(signUpDto.getPlan())
                .activeSession(SessionCountMapper.getActiveSessionBasedOnSubscription(signUpDto.getPlan()))
                .build();
        toBeCreatedUser.setSubscription(subscriptionEntity);

        Users savedUser = userRepository.save(toBeCreatedUser);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException("User with email "+ username +" not found"));
    }
}
