package com.greenfox.javatribes.javatribes.restcontrollers;

import com.greenfox.javatribes.javatribes.exceptions.ResponseObject;
import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.security.JWTTokenUtil;
import com.greenfox.javatribes.javatribes.service.UserService;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginRestController {

    private UserService userService;
    private JWTTokenUtil jwtTokenUtil;

    public LoginRestController(UserService userService, JWTTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody User user) {

        try {
            User authUser = userService.findByCredentials(user.getUsername(), user.getPassword());
            return ResponseEntity.status(HttpStatus.valueOf(200)).body(new ResponseObject("ok",
                    null, jwtTokenUtil.getToken(authUser)));
        } catch (NotFoundException error) {
            return ResponseEntity.status(HttpStatus.valueOf(401)).body(new ResponseObject("error",
                    error.getMessage(), null));
        } catch (IllegalArgumentException error) {
            return ResponseEntity.status(HttpStatus.valueOf(400)).body(new ResponseObject("error",
                    error.getMessage(), null));
        }
    }
}

        // Return the token

//        if (user.authenticated){
//            return ResponseEntity.status(HttpStatus.valueOf(200)).build();
////            {"status" : "ok", "token" : "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.
////            eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.
////            TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ"}
//        } else {
//            return  ResponseEntity.status(HttpStatus.valueOf(401)).build();
////            { ""status"" : ""error"", ""message"" : ""No such user: <username>!"" }
////            { ""status"" : ""error"", ""message"" : ""Wrong password!"" }
//        }

