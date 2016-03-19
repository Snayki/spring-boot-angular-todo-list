package com.todo.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.todo.entity.User;
import com.todo.utils.TokenService;
import com.todo.utils.jackson.View;
import com.todo.utils.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.todo.utils.Constants.X_TOKEN;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private TokenService tokenService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public void login() {
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public void logout(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException {
        SecurityUtils.deleteCookie(httpResponse);

        Optional<String> token = Optional.ofNullable(httpRequest.getHeader(X_TOKEN));
        tokenService.clear(token.get());

        httpRequest.logout();
    }

    @RequestMapping(value = "logged", method = RequestMethod.GET)
    public Boolean isAuthenticated() {
        return SecurityUtils.isAuthenticated();
    }

    @JsonView(View.Public.class)
    @RequestMapping(value = "getUser", method = RequestMethod.GET)
    public User getUser() {
        return SecurityUtils.getAuthenticatedUser();
    }
}
