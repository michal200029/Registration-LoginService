package pl.opalka.Registration.Login.Service.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;


@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final String USERNAME_CANNOT_BE_NULL = "Username cannot be null";
    private static final String CREDENTIALS_CANNOT_BE_NULL = "Credentials cannot be null";
    private static final String INCORRECT_PASSWORD = "Incorrect password";


    private final UserDetailsServiceImp userDetailsServiceImp;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public CustomAuthenticationProvider(UserDetailsServiceImp userDetailsServiceImp) {
        this.userDetailsServiceImp = userDetailsServiceImp;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        Object credentials = authentication.getCredentials();
        Assert.notNull(username, USERNAME_CANNOT_BE_NULL);
        Assert.notNull(credentials, CREDENTIALS_CANNOT_BE_NULL);

        if(credentials instanceof String)
            return null;

        String password = credentials.toString();

        UserDetails userDetails = userDetailsServiceImp.loadUserByUsername(username);

        if(!bCryptPasswordEncoder.matches(password, userDetails.getPassword()))
             throw new BadCredentialsException(INCORRECT_PASSWORD);



        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password,
                userDetails.getAuthorities());

        return usernamePasswordAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}


