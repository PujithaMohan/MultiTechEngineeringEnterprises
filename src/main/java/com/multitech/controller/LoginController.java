package com.multitech.controller;

import com.multitech.global.GlobalData;
import com.multitech.model.Role;
import com.multitech.model.User;
import com.multitech.repository.RoleRepository;
import com.multitech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/login")
    public String loginPage(){
        GlobalData.cart.clear();
        return "login";
    }
    @GetMapping("/register")
    public String registerPage(){
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user")User user, HttpServletRequest httpServletRequest)throws ServletException
    {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        List<Role> roleList=new ArrayList<>();
        roleList.add(roleRepository.findById(2).get());
        user.setRoles(roleList);
        userRepository.save(user);
        httpServletRequest.login(user.getEmail(),user.getPassword());
        return "redirect:/";
    }
}
