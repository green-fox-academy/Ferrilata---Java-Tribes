package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.dto.RegisterObject;
import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.Role;
import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.repositories.UserRepository;
import com.greenfox.javatribes.javatribes.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public String login(String username, String password) throws CustomException {
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return this.jwtTokenProvider.createToken(username, findByUsername(username).getRoles());
        } catch (AuthenticationException e) {
            throw new CustomException("No such user - wrong username or password.", HttpStatus.valueOf(401));
        }
    }

    @Override
    public User getUserFromToken(HttpServletRequest httpServletRequest) {

        return this.userRepository.findByUsername(this.jwtTokenProvider.getUsername(this.jwtTokenProvider.resolveToken(httpServletRequest))).get();
    }

    @Override
    public User register(RegisterObject registerObject) throws CustomException {

        Kingdom newKingdom = new Kingdom(registerObject.getKingdom());
        User newUser = new User(registerObject.getUsername(), registerObject.getPassword(), newKingdom);

        if (!existsByUsername(newUser.getUsername()) && !existsByKingdomName(newUser.getKingdom().getName())) {
            newUser.setPassword(this.passwordEncoder.encode(newUser.getPassword()));
            if (newUser.getRoles().isEmpty() || newUser.getRoles() == null) {
                newUser.addRole(Role.ROLE_USER);
            }
            this.userRepository.save(newUser);
        }
        return newUser;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Kingdom updateKingdom(Kingdom kingdom, String name, int locationX, int locationY) {

        kingdom.setName(name);
        kingdom.setLocationX(locationX);
        kingdom.setLocationY(locationY);
        this.userRepository.save(kingdom.getUser());

        return kingdom;
    }

    @Override
    public Boolean existsByUsername(String username) throws CustomException {
        boolean optionalUser = this.userRepository.existsByUsername(username);

        if (optionalUser) {
            throw new CustomException("Username already taken, please choose an other one.", HttpStatus.valueOf(409));
        }
        return false;
    }

    @Override
    public boolean existsByKingdomName(String name) throws CustomException {
        boolean isKingdomNamePresent = this.userRepository.existsByKingdomName(name);
        if (isKingdomNamePresent) {
            throw new CustomException("Kingdom already taken, please choose an other one.", HttpStatus.valueOf(409));
        }
        return false;
    }

    @Override
    public User findByUsername(String username) throws CustomException {

        Optional<User> optionalUser = this.userRepository.findByUsername(username);

        if (!optionalUser.isPresent()) {
            throw new CustomException("No such user - wrong username.", HttpStatus.valueOf(401));
        }
        return optionalUser.get();
    }

    @Override
    public User findById(long id) throws CustomException {

        Optional<User> optionalUser = this.userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            throw new CustomException("UserId not found!", HttpStatus.valueOf(404));
        }
        return optionalUser.get();
    }

}
