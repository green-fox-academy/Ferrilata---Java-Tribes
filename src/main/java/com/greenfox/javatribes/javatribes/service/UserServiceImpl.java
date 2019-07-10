package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.exceptions.EntityNotFoundException;
import com.greenfox.javatribes.javatribes.exceptions.IdentityAlreadyUsedException;
import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.repositories.KingdomRepository;
import com.greenfox.javatribes.javatribes.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private KingdomRepository kingdomRepository;

    public UserServiceImpl(UserRepository userRepository, KingdomRepository kingdomRepository) {
        this.userRepository = userRepository;
        this.kingdomRepository = kingdomRepository;
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) throws CustomException{

        Optional<User> optionalUser = userRepository.findByUsernameAndPassword(username, password);

        if (!optionalUser.isPresent()){
            throw new CustomException("No such user - wrong username or password.", HttpStatus.valueOf(401));
        }

        return optionalUser.get();
    }

    @Override
    public void saveUser(User user){
        if(!existsByUsername(user.getUsername()) && !existsByKingdomName(user.getKingdom().getName()));
        userRepository.save(user);
    }

    @Override
    public Boolean existsByUsername(String username) throws CustomException {
        boolean optionalUser = userRepository.existsByUsername(username);

        if (optionalUser){
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
}
