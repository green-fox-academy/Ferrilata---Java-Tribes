package com.greenfox.javatribes.javatribes.restcontrollers;

import com.greenfox.javatribes.javatribes.dto.RegisterObject;
import com.greenfox.javatribes.javatribes.dto.ResponseObject;
import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.Supply;
import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserRestController {

    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody @Valid User user) throws CustomException {

        return ResponseEntity.status(HttpStatus.valueOf(200)).body(new ResponseObject("ok",
                null, userService.login(user.getUsername(), user.getPassword())));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid RegisterObject registerObject) throws CustomException {

        if (registerObject.getKingdom().isEmpty()) {
            registerObject.setKingdom(registerObject.getUsername() + "'s kingdom");
        }

        Kingdom newKingdom = new Kingdom(registerObject.getKingdom());

        Supply gold = new Supply("gold", 1000, newKingdom);
        Supply food = new Supply("food", 1000, newKingdom);

        newKingdom.addSupply(gold);
        newKingdom.addSupply(food);

        User newUser = new User(registerObject.getUsername(), registerObject.getPassword(), newKingdom);
        userService.register(newUser);

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