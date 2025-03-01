package com.aman.SpringSecurity.SpringSecurity.Controllers;

import com.aman.SpringSecurity.SpringSecurity.DTO.UserDTO;
import com.aman.SpringSecurity.SpringSecurity.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable Long userId){
        return userService.getUserById(userId);
    }

    @GetMapping("/{userEmail}")
    public UserDTO getUserByEmail(@PathVariable String emailId){
        return userService.getUserByEmail(emailId);
    }

    @PostMapping
    public UserDTO createNewUser(@RequestBody UserDTO userDTO){
        return userService.save(userDTO);
    }
}
