package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.EntityNotFoundException;
import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.repositories.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) throws EntityNotFoundException{

        Optional<User> optionalUser = userRepository.findByUsernameAndPassword(username, password);

        if (!optionalUser.isPresent()){
            throw new EntityNotFoundException("No such user - wrong username or password.");
        }

        return optionalUser.get();
    }
}
