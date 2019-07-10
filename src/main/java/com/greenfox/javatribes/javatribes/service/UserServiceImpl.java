package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.EntityNotFoundException;
import com.greenfox.javatribes.javatribes.exceptions.UsernameAlreadyUsedException;
import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository userRepository;
//    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
    }



    @Override
    public User findByUsernameAndPassword(String username, String password) throws EntityNotFoundException{

        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (!optionalUser.isPresent() || !new BCryptPasswordEncoder().matches(password, optionalUser.get().getPassword())){
            throw new EntityNotFoundException("No such user - wrong username or password.");
        }
        return optionalUser.get();
    }

    @Override
    public void saveUser(User user) throws UsernameAlreadyUsedException {
        if(!existsByUsername(user.getUsername()))
        userRepository.save(user);
    }

    @Override
    public Boolean existsByUsername(String username) throws UsernameAlreadyUsedException {
        boolean optionalUser = userRepository.existsByUsername(username);

        if (optionalUser){
            throw new UsernameAlreadyUsedException("Username already taken, please choose an other one.");
        }

        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (!optionalUser.isPresent()){
            throw new UsernameNotFoundException("No such user - wrong username.");
        }

        return (UserDetails) optionalUser.get();
    }
}
