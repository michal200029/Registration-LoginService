package pl.opalka.Registration.Login.Service.exceptions;

import org.springframework.beans.factory.annotation.Autowired;

public class UserException extends RuntimeException{

    private UserError userError;

    @Autowired
    public UserException(UserError userError){
        this.userError=userError;
    }

    public UserError getUserError() {
        return userError;
    }
}
