package spring.boot_security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.boot_security.configs.WebSecurityConfig;
import spring.boot_security.models.User;
import spring.boot_security.repository.UserRepository;
import java.util.List;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(long id) {
        return userRepository.getById((long) Math.toIntExact(id));
    }

    @Override
    public User save(User user) {
        user.setPassword(WebSecurityConfig.getPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public void update(long id, User updateUser) {
        updateUser.setId(id);
        updateUser.setPassword(WebSecurityConfig.getPasswordEncoder().encode(updateUser.getPassword()));
        userRepository.saveAndFlush(updateUser);
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(email);
        if (user == null) {
            throw new IllegalArgumentException(String.format("User '%s' not found", email));
        }
        return user;
    }
}
