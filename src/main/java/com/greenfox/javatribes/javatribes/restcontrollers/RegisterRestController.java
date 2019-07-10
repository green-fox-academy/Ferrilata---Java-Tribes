package com.greenfox.javatribes.javatribes.restcontrollers;

import com.greenfox.javatribes.javatribes.exceptions.UsernameAlreadyUsedException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.dto.RegisterObject;
import com.greenfox.javatribes.javatribes.model.Role;
import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.service.KingdomService;
import com.greenfox.javatribes.javatribes.service.RoleService;
import com.greenfox.javatribes.javatribes.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
public class RegisterRestController {

    private UserService userService;
    private KingdomService kingdomService;
    private RoleService roleService;

//    @Autowired
//    @Qualifier("register")
//    private PasswordEncoder passwordEncoder2;
//
//    @Bean
//    @Qualifier("register")
//    public PasswordEncoder PasswordEncoder2() {
//        return new BCryptPasswordEncoder();
//    }

    public RegisterRestController(UserService userService, KingdomService kingdomService, RoleService roleService) {
        this.userService = userService;
        this.kingdomService = kingdomService;
        this.roleService = roleService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid RegisterObject registerObject) throws UsernameAlreadyUsedException {


//        userService.encodePassword(registerObject.getPassword());
        Role userRole = roleService.findByName("ROLE_USER");

        User newUser = new User(registerObject.getUsername(),
                                new BCryptPasswordEncoder().encode(registerObject.getPassword()),
//                                registerObject.getPassword(),
                                new Kingdom(registerObject.getKingdom()));
        newUser.addRole(userRole);
        userRole.addUser(newUser);

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