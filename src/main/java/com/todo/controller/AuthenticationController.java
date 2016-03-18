package com.todo.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.todo.entity.User;
import com.todo.utils.jackson.View;
import com.todo.utils.security.SecurityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
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
