package com.greenfox.javatribes.javatribes.restcontrollers;

import com.greenfox.javatribes.javatribes.exceptions.EntityNotFoundException;
import com.greenfox.javatribes.javatribes.model.ResponseObject;
import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.security.JWTTokenUtil;
import com.greenfox.javatribes.javatribes.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LoginRestController {

    private UserService userService;
    private JWTTokenUtil jwtTokenUtil;

    public LoginRestController(UserService userService, JWTTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody @Valid User user) throws EntityNotFoundException {

            User authUser = userService.findByUsernameAndPassword(user.getUsername(), user.getPassword());

            return ResponseEntity.status(HttpStatus.valueOf(200)).body(new ResponseObject("ok",
                    null, jwtTokenUtil.getToken(authUser)));
    }
}
