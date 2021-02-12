package pl.opalka.Registration.Login.Service.services;

import pl.opalka.Registration.Login.Service.entity.User;

public interface UserService {
    User setEmbeddedRoleToNewUser(User newUser);
    User createUser(String username, String password, String email,String embeddedRole );
}
