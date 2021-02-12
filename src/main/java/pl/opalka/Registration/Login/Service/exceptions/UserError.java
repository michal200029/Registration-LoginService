package pl.opalka.Registration.Login.Service.exceptions;



public enum UserError {

    USERNAME_EXISTS("User with given username already exists!"),
    EMAIL_EXISTS("User with given email already exists!"),
    SUCCESS("Registration successful! ");

    private  String message;

    UserError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
