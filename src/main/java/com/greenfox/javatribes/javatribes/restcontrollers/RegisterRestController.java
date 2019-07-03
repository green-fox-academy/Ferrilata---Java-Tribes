package com.greenfox.javatribes.javatribes.restcontrollers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.greenfox.javatribes.javatribes.exceptions.UsernameAlreadyUsedException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.RegisterObject;
import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.service.KingdomService;
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
    private KingdomService kingdomService;

    public RegisterRestController(UserService userService, KingdomService kingdomService) {
        this.userService = userService;
        this.kingdomService = kingdomService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid RegisterObject registerObject) throws UsernameAlreadyUsedException, JsonProcessingException {

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