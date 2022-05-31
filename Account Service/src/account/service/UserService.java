package account.service;

import account.Exception.UserExistsException;
import account.model.User;
import account.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    public static final int MAX_FAILED_ATTEMPTS = 5;

    @Autowired
    UserRepository userRepo;

    public User save(User toSave) {
        return userRepo.save(toSave);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByEmailIgnoreCase(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Email not registered");
        }
        return user.get();
    }

    public void loadUserByEmail(String email) throws UserExistsException {
        Optional<User> user = userRepo.findByEmailIgnoreCase(email);
        if (user.isPresent()){
            throw new UserExistsException();
        }
    }

    public boolean isAnyUser(){
        return userRepo.count() > 0 ? true : false;
    }

    public List<User> loadAllUsers(){
        return userRepo.findAllByOrderByIdAsc();
    }

    public void deleteUser(User user){
        userRepo.delete(user);
    }

    public void increaseFailedAttempts(User user) {
        int newFailAttempts = user.getFailedAttempt() + 1;
        userRepo.updateFailedAttempts(newFailAttempts, user.getEmail());
    }

    public void resetFailedAttempts(String email) {
        userRepo.updateFailedAttempts(0, email);
    }

    public void lock(User user) {
        user.setAccountNonLocked(false);
        user.setLockTime(new Date());

        userRepo.save(user);
    }

    public void unlock(User user) {
        user.setAccountNonLocked(true);
        user.setFailedAttempt(0);
        user.setLockTime(null);

        userRepo.save(user);
    }

    public Optional<User> findFirstByEmail(String email){
        return userRepo.findFirstByEmailIgnoreCase(email);
    }



}
