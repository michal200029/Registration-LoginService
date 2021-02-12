package pl.opalka.Registration.Login.Service.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class SignInController {


    @GetMapping("/login")
    public ModelAndView signIn(ModelAndView mav){
        mav.setViewName("login");
        return mav;
    }


    @GetMapping("/user_panel")
    public ModelAndView userPanel(ModelAndView mav) {
        mav.setViewName("user_panel");

        return mav;
    }

    @GetMapping("/admin_panel")
    public ModelAndView adminPanel(ModelAndView mav) {
        mav.setViewName("admin_panel");

        return mav;
    }

    @GetMapping("/guest_panel")
    public ModelAndView guestPanel(ModelAndView mav) {
        mav.setViewName("guest_panel");

        return mav;
    }



}
