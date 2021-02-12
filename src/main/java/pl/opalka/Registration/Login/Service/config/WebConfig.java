package pl.opalka.Registration.Login.Service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.opalka.Registration.Login.Service.authentication.CustomAuthenticationProvider;
import pl.opalka.Registration.Login.Service.authentication.UserDetailsServiceImp;

@Configuration
public class WebConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImp userDetailsServiceImp;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public WebConfig(UserDetailsServiceImp userDetailsServiceImp, CustomAuthenticationProvider customAuthenticationProvider, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImp);
        auth.authenticationProvider(customAuthenticationProvider);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin_panel").hasAuthority("ADMIN")
                .antMatchers("/user_panel").hasAnyAuthority("ADMIN","USER","ROLE_USER")
                .antMatchers("/guest_panel").hasAnyAuthority("ADMIN","USER","GUEST","ROLE_USER")
                .antMatchers("/oauth2/**").permitAll()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/guest_panel", true)
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .deleteCookies("cookies")
                .and()
                .oauth2Login()
                    .loginPage("/login")
                    .defaultSuccessUrl("/guest_panel")
                .and()
                .csrf().disable();
    }
}
