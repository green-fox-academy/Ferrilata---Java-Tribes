package com.greenfox.javatribes.javatribes.restcontrollers;

import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.security.JWTTokenUtil;
import com.greenfox.javatribes.javatribes.service.UserService;
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
    public ResponseEntity<Object> loginUser (@RequestBody User user){

        User authUser = userService.findUserByUsername(user.getUsername());
        String token = jwtTokenUtil.createJWT(authUser);

        // Return the token
        return ResponseEntity.ok(token);

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
    }
}
