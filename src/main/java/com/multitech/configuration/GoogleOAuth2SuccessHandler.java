package com.multitech.configuration;

import com.multitech.model.Role;
import com.multitech.model.User;
import com.multitech.repository.RoleRepository;
import com.multitech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    private RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken oAuth2AuthenticationToken=(OAuth2AuthenticationToken) authentication;
        String email=oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email").toString();//OpenIDConnect Scope:"email" Claims:"email"
        if(!userRepository.findUserByEmail(email).isPresent()){
            User user=new User();
            user.setFirstName(oAuth2AuthenticationToken.getPrincipal().getAttributes().get("given_name").toString()); //OpenIDConnect Scope:"profile" Claims:"given_name"
            user.setLastName(oAuth2AuthenticationToken.getPrincipal().getAttributes().get("family_name").toString()); //OpenIDConnect Scope:"profile" Claims:"family_name"
            user.setEmail(email);
            List<Role> roleList=new ArrayList<>();
            roleList.add(roleRepository.findById(2).get());
            user.setRoles(roleList);
            userRepository.save(user);
        }
        redirectStrategy.sendRedirect(request,response,"/");

    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken oAuth2AuthenticationToken=(OAuth2AuthenticationToken) authentication;
        String email=oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email").toString();//OpenIDConnect Scope:"email" Claims:"email"
        if(!userRepository.findUserByEmail(email).isPresent()){
            User user=new User();
            user.setFirstName(oAuth2AuthenticationToken.getPrincipal().getAttributes().get("given_name").toString()); //OpenIDConnect Scope:"profile" Claims:"given_name"
            user.setLastName(oAuth2AuthenticationToken.getPrincipal().getAttributes().get("family_name").toString()); //OpenIDConnect Scope:"profile" Claims:"family_name"
            user.setEmail(email);
            List<Role> roleList=new ArrayList<>();
            roleList.add(roleRepository.findById(2).get());
            user.setRoles(roleList);
            userRepository.save(user);
        }
        redirectStrategy.sendRedirect(httpServletRequest,httpServletResponse,"/");

    }

}
