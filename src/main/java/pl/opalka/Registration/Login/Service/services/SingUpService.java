package pl.opalka.Registration.Login.Service.services;


import pl.opalka.Registration.Login.Service.exceptions.UserError;


public interface SingUpService {
      UserError singUpUser(String username, String email, String password,String embeddedRole );
}
