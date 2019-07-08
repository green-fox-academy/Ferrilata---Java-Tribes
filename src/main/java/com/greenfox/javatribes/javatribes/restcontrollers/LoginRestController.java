package com.greenfox.javatribes.javatribes.restcontrollers;

import com.greenfox.javatribes.javatribes.exceptions.EntityNotFoundException;
import com.greenfox.javatribes.javatribes.model.ResponseObject;
import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.security.JWTTokenUtil;
import com.greenfox.javatribes.javatribes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
public class LoginRestController {

    private UserService userService;
    private JWTTokenUtil jwtTokenUtil;

//    @Autowired
//    @Qualifier("login")
//    private PasswordEncoder passwordEncoder3;
//
//    @Bean
//    @Qualifier("login")
//    public PasswordEncoder PasswordEncoder3() {
//        return new BCryptPasswordEncoder();
//    }

    public LoginRestController(UserService userService, JWTTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody @Valid User user) throws EntityNotFoundException {

            User authUser = userService.findByUsernameAndPassword(user.getUsername(),
                    user.getPassword());

            return ResponseEntity.status(HttpStatus.valueOf(200)).body(new ResponseObject("ok",
                    null, jwtTokenUtil.getToken(authUser)));
    }

//    THIS ENDPOINT IS CREATED ONLY FOR AUTHENTICATION TESTING PURPOSES
    @GetMapping("/tribes")
    public String initialEndpointAfterLogin (){
        return "YOU ARE PLAYING NOW";
    }
}
