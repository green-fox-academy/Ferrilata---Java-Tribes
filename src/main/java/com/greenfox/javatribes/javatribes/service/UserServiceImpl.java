package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Role;
import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.repositories.UserRepository;
import com.greenfox.javatribes.javatribes.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String login(String username, String password) throws CustomException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(username, findByUsername(username).getRoles());
        } catch (AuthenticationException e) {
            throw new CustomException("No such user - wrong username or password.", HttpStatus.valueOf(401));
        }
    }

    @Override
    public User getUserFromToken(HttpServletRequest httpServletRequest) {

        return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(httpServletRequest))).get();

    }

    @Override
    public void register(User user) throws CustomException {

        if (!existsByUsername(user.getUsername()) && !existsByKingdomName(user.getKingdom().getName())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            if (user.getRoles() == null) {
                user.setRoles(new ArrayList<>(Arrays.asList(Role.ROLE_USER)));
            }
            userRepository.save(user);
        }
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Boolean existsByUsername(String username) throws CustomException {
        boolean optionalUser = userRepository.existsByUsername(username);

        if (optionalUser) {
            throw new CustomException("Username already taken, please choose an other one.", HttpStatus.valueOf(409));
        }
        return false;
    }

    @Override
    public boolean existsByKingdomName(String name) throws CustomException {
        boolean isKingdomNamePresent = userRepository.existsByKingdomName(name);
        if (isKingdomNamePresent) {
            throw new CustomException("Kingdom already taken, please choose an other one.", HttpStatus.valueOf(409));
        }
        return false;
    }

    @Override
    public User findByUsername(String username) throws CustomException {

        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (!optionalUser.isPresent()) {
            throw new CustomException("No such user - wrong username.", HttpStatus.valueOf(401));
        }
        return optionalUser.get();
    }

    @Override
    public User findById(long id) throws CustomException {

        Optional<User> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            throw new CustomException("UserId not found!", HttpStatus.valueOf(404));
        }
        return optionalUser.get();
    }

}
