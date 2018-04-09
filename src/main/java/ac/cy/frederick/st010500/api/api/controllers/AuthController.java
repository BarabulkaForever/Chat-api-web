package ac.cy.frederick.st010500.api.api.controllers;


import ac.cy.frederick.st010500.api.api.models.User;
import ac.cy.frederick.st010500.api.api.services.AuthService;
import ac.cy.frederick.st010500.api.api.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AuthController {
    private UserService userService;
    private AuthService authService;

    @Autowired
    public AuthController(UserService userService, AuthService authService) {
        this.authService = authService;
        this.userService = userService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Object registration(@Valid User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors())
            {
                FieldError fieldError = (FieldError)error;
                errors.add(String.format("%s: %s", fieldError.getField(), error.getDefaultMessage()));
            }
            return  errors;
        }

        String password = user.getPassword();

        userService.saveUser(user);

        String accessToken = authService.autologin(user.getUsername(), password);

        return user;
    }

//    // Process form input data
//    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    @ResponseStatus(HttpStatus.CREATED)
//    @ResponseBody
//    public Object register(@Valid User user, BindingResult bindingResult, HttpServletRequest request) throws JsonProcessingException {
//
//        if (bindingResult.hasErrors()) {
//            List<String> errors = new ArrayList<>();
//            for (ObjectError error : bindingResult.getAllErrors())
//            {
//                FieldError fieldError = (FieldError)error;
//                errors.add(String.format("%s: %s", fieldError.getField(), error.getDefaultMessage()));
//            }
//            return  errors;
//        }
//
//        // Lookup user in database by e-mail
//        Optional<User> userExists = userService.findById(user.getId());
//
//        if (userExists.isPresent()) {
//            bindingResult.reject("username");
//
//            return "Username is already taken.";
//        }
//
//        if (!bindingResult.hasErrors()) {
//            user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
//            userService.saveUser(user);
//        }
//
//        return  user;
//    }

}