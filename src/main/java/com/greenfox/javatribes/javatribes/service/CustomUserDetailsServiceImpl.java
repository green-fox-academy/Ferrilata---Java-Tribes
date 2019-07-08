package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.model.CustomUserDetails;
import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {


    private final UserRepository userRepository;

    public CustomUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        optionalUser
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        return optionalUser
                .map(CustomUserDetails::new).get();
    }
}
