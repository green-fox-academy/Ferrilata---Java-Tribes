package com.greenfox.javatribes.javatribes.restcontrollers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.greenfox.javatribes.javatribes.exceptions.IdentityAlreadyUsedException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.RegisterObject;
import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegisterRestController {

    private UserService userService;

    public RegisterRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid RegisterObject registerObject) throws IdentityAlreadyUsedException, JsonProcessingException {

        if(registerObject.getKingdom().isEmpty()) {
            registerObject.setKingdom(registerObject.getUsername() + "'s kingdom");
        }

        User newUser = new User(registerObject.getUsername(), registerObject.getPassword(), new Kingdom(registerObject.getKingdom()));
        userService.saveUser(newUser);

            return ResponseEntity.status(HttpStatus.valueOf(200)).body(newUser);
    }
}

//IF WE WANT TO USE JSON FILTER
//        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//        filterProvider.addFilter("KingdomFilter", SimpleBeanPropertyFilter.filterOutAllExcept("kingdomId"));
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.setFilterProvider(filterProvider);
//        String jsonData = mapper.writerWithDefaultPrettyPrinter()
//                .writeValueAsString(newUser);