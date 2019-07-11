//package com.greenfox.javatribes.javatribes.restcontrollers;
//
//import com.greenfox.javatribes.javatribes.exceptions.CustomException;
//import com.greenfox.javatribes.javatribes.dto.ResponseObject;
//import com.greenfox.javatribes.javatribes.model.User;
//import com.greenfox.javatribes.javatribes.security.JwtTokenProvider;
//import com.greenfox.javatribes.javatribes.service.UserService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.validation.Valid;
//
//@RestController
//public class LoginRestController {
//
//    private UserService userService;
//    private JwtTokenProvider jwtTokenProvider;
//
//    public LoginRestController(UserService userService, JwtTokenProvider jwtTokenProvider) {
//        this.userService = userService;
//        this.jwtTokenProvider = jwtTokenProvider;
//    }
//
//    @PostMapping("/loginTMP")
//    public ResponseEntity<Object> loginUser(@RequestBody @Valid User user) throws CustomException {
//
//            User authUser = userService.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//
//            return ResponseEntity.status(HttpStatus.valueOf(200)).body(new ResponseObject("ok",
//                    null, jwtTokenProvider.createToken(authUser.getUsername(), authUser.getRoles())));
//    }
//}
