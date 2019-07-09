package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.EntityNotFoundException;
import com.greenfox.javatribes.javatribes.exceptions.IdentityAlreadyUsedException;
import com.greenfox.javatribes.javatribes.exceptions.UserIdNotFoundException;
import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.repositories.KingdomRepository;
import com.greenfox.javatribes.javatribes.repositories.UserRepository;
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
    public User findByUsernameAndPassword(String username, String password) throws EntityNotFoundException{

        Optional<User> optionalUser = userRepository.findByUsernameAndPassword(username, password);

        if (!optionalUser.isPresent()){
            throw new EntityNotFoundException("No such user - wrong username or password.");
        }

        return optionalUser.get();
    }

    @Override
    public User findById(long id) throws UserIdNotFoundException {

        Optional<User> optionalUser = userRepository.findById(id);

        if(!optionalUser.isPresent()) {
            throw new UserIdNotFoundException("UserId not found");
        }

        return optionalUser.get();

    }

    @Override
    public void saveUser(User user) throws IdentityAlreadyUsedException {
        if(!existsByUsername(user.getUsername()) && !existsByKingdomName(user.getKingdom().getName()));
        userRepository.save(user);
    }

    @Override
    public Boolean existsByUsername(String username) throws IdentityAlreadyUsedException {
        boolean optionalUser = userRepository.existsByUsername(username);

        if (optionalUser){
            throw new IdentityAlreadyUsedException("Username already taken, please choose an other one.");
        }

        return false;
    }

    @Override
    public boolean existsByKingdomName(String name) throws IdentityAlreadyUsedException {
        boolean isKingdomNamePresent = userRepository.existsByKingdomName(name);
        if (isKingdomNamePresent) {
            throw new IdentityAlreadyUsedException("Kingdom already taken, please choose an other one.");
        }
        return false;
    }
}
