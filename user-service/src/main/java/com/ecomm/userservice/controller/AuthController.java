package com.ecomm.userservice.controller;

import com.ecomm.userservice.dto.SignupRequest;
import com.ecomm.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/")
    public String home() {
        return "index";
    }
    
    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }
    
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("signupRequest", new SignupRequest());
        return "register";
    }
    
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("signupRequest") SignupRequest signupRequest,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "register";
        }
        
        if (userService.existsByUsername(signupRequest.getUsername())) {
            result.rejectValue("username", "error.signupRequest", "Username already exists!");
            return "register";
        }
        
        if (userService.existsByEmail(signupRequest.getEmail())) {
            result.rejectValue("email", "error.signupRequest", "Email already registered!");
            return "register";
        }
        
        userService.registerUser(signupRequest);
        redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
        return "redirect:/login";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        return "dashboard";
    }
    
    @GetMapping("/profile")
    public String profile(Model model) {
        return "profile";
    }
}
