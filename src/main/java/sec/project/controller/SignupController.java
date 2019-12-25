package sec.project.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {

    @Autowired
    private SignupRepository signupRepository;

//    @Autowired
//    private PasswordEncoder encoder;

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/form";
    }

    @RequestMapping("/form")
    public String passwordForm() {
        return "form";
    }

    @RequestMapping("/denied")
    public String deniedAdmin() {
        return "denied";
    }

    @RequestMapping("/access")
    public String accessAdmin() {
        return "access";
    }

    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public String changePassword(Authentication authentication, @RequestParam String password) {
        Signup signup = signupRepository.findByName(authentication.getName());
        if (signup == null) {
            return "redirect:/index";
        }

//        signup.setPassword(encoder.encode(password));
        signupRepository.save(signup);

        return "done";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        return "form";
    }

    @RequestMapping(value = "/form2", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String password) {
        Signup signup = new Signup();
        signup.setName(name);
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String hashedPassword = passwordEncoder.encode(password);
        signup.setPassword(password);
        
        signupRepository.save(signup);
        return "done";
    }

    @RequestMapping(value = "/form3", method = RequestMethod.POST)
    public String submitForm2(@RequestParam String name, @RequestParam String password) {
        if (name.equals("admin") && password.equals(password)) {
            return "access";
        }
        return "denied";
    }

}
