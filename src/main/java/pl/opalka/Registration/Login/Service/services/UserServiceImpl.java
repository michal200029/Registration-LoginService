package pl.opalka.Registration.Login.Service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.opalka.Registration.Login.Service.entity.Role;
import pl.opalka.Registration.Login.Service.entity.User;
import pl.opalka.Registration.Login.Service.repository.RoleRepository;


import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepository;

    private final String UNCONFIRMEDROLE = "GUEST";

    @Autowired
    public UserServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public User setEmbeddedRoleToNewUser(User newUser){
        /** BEFORE EMAIL CONFIRMATION EVERY USER HAS GUEST ROLE */
        Optional<Role> roleOptional = roleRepository.findByName(UNCONFIRMEDROLE);
        if (roleOptional.isPresent())
            newUser.getRoles().add(roleOptional.get());
        return newUser;
    }

    public User createUser(String username, String password, String email, String embeddedRole ) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setEnabled(false);
        user.setEmbeddedrole(embeddedRole);
        user.setRoles(setEmbeddedRoleToNewUser(user).getRoles());


        return user;
    }
}
