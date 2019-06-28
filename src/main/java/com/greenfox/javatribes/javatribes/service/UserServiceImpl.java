package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.repositories.UserRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByCredentials(String username, String password) throws NotFoundException {

        if(username == null || password == null || username.isEmpty() || password.isEmpty()){

            List tempList = new ArrayList<>();

            if(username == null || username.isEmpty()) {
                tempList.add("username");
            }
            if(password == null || password.isEmpty()) {
                tempList.add("password");
            }
            String missParam = tempList.toString();

            throw new IllegalArgumentException("Missing parameter(s): " + missParam);
            }

        Optional<User> optionalUser = userRepository.findByUsernameAndPassword(username, password);

        if (!optionalUser.isPresent()){
            throw new NotFoundException("No such user - wrong username or password.");
        }

        return optionalUser.get();
    }
}
