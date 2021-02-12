package pl.opalka.Registration.Login.Service.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.opalka.Registration.Login.Service.entity.User;
import pl.opalka.Registration.Login.Service.repository.UserRepository;

import java.util.Optional;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if(optionalUser.isEmpty())
            throw new UsernameNotFoundException("User was not found !");

        User confirmedUser= optionalUser.get();

        return confirmedUser;
    }
}
