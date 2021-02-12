package pl.opalka.Registration.Login.Service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.opalka.Registration.Login.Service.entity.User;
import pl.opalka.Registration.Login.Service.exceptions.UserError;
import pl.opalka.Registration.Login.Service.mailer.SingUpMailer;
import pl.opalka.Registration.Login.Service.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class SingUpServiceImpl implements SingUpService {

    private final UserRepository userRepository;
    private final SingUpMailer singUpMailer;
    private final UserService userService;


    @Bean
    private BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public SingUpServiceImpl(UserRepository userRepository, SingUpMailer singUpMailer, UserService userService) {
        this.userRepository = userRepository;
        this.singUpMailer = singUpMailer;
        this.userService = userService;
    }

    @Override
    public UserError singUpUser(String username, String password, String email, String embeddedRole ) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isPresent()) {
            return  UserError.USERNAME_EXISTS;
        }

        optionalUser = userRepository.findByEmail(email.toLowerCase());
        if(optionalUser.isPresent()){
           return UserError.EMAIL_EXISTS;
        }

        password = bCryptPasswordEncoder().encode(password);
        User user = userService.createUser(username, password, email,embeddedRole);


        user.setToken(UUID.randomUUID().toString());
        singUpMailer.sendConfirmationLink(user.getEmail(),user.getToken());

        userRepository.save(user);

        return UserError.SUCCESS;

    }
}
