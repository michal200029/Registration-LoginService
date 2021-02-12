package pl.opalka.Registration.Login.Service.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.opalka.Registration.Login.Service.entity.Role;
import pl.opalka.Registration.Login.Service.entity.User;
import pl.opalka.Registration.Login.Service.exceptions.UserError;

import pl.opalka.Registration.Login.Service.mailer.SingUpMailer;
import pl.opalka.Registration.Login.Service.repository.RoleRepository;
import pl.opalka.Registration.Login.Service.repository.UserRepository;
import pl.opalka.Registration.Login.Service.services.SingUpService;

import java.util.Optional;


@Controller
public class SignUpController {

    private final SingUpService singUpService;
    private final UserRepository userRepository;
    private final SingUpMailer singUpMailer;
    private final RoleRepository roleRepository;

    @Autowired
    public SignUpController(SingUpService singUpService, UserRepository userRepository, SingUpMailer singUpMailer, RoleRepository roleRepository) {
        this.singUpService = singUpService;
        this.userRepository = userRepository;
        this.singUpMailer = singUpMailer;
        this.roleRepository = roleRepository;
    }

    @GetMapping(value="/sign_up")
    public ModelAndView singUp(ModelAndView mav) {
        mav.setViewName("sign_up");
        return mav;
    }

    @PostMapping(value="/sign_up")
    public ModelAndView singUpPost(ModelAndView mav,
                                   @RequestParam("username") String username,
                                   @RequestParam("email") String email,
                                   @RequestParam("password") String password,
                                   @RequestParam("roles") String embeddedRole) {


        UserError us = singUpService.singUpUser(username,password,email,embeddedRole);
        mav.addObject("message",us.getMessage());
        if(UserError.USERNAME_EXISTS.equals(us) || UserError.EMAIL_EXISTS.equals(us))
            mav.setViewName("redirect:/sign_up_Error");
        else if(UserError.SUCCESS.equals(us))
            mav.setViewName("redirect:/login");

        return mav;
    }

    @GetMapping("/confirm_account")
    public ModelAndView sendConformationEmail(@RequestParam(name="token") String token, ModelAndView mav){

        Optional<User> optionalUser = userRepository.findByToken(token);
        mav.setViewName("/confirm_page");
        if(optionalUser.isPresent()){

            User user = optionalUser.get();

            Optional<Role> optionalRole = roleRepository.findByName(user.getEmbeddedrole());
            if (optionalRole.isPresent()) {
                user.getRoles().clear();
                user.getRoles().add(optionalRole.get());

                user.setEnabled(true);
                userRepository.save(user);

                mav.addObject("message","Your account has been activated" );
            }

        }else
            mav.addObject("message","Given token does not exist. Try once again" );

        return mav;
    }


 }
