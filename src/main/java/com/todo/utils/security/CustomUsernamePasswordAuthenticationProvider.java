package com.todo.utils.security;

import com.todo.entity.User;
import com.todo.service.user.UserService;
import com.todo.utils.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by Snayki on 18.03.2016.
 */
@Component
public class CustomUsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Optional<String> username = (Optional) authentication.getPrincipal();
        Optional<String> password = (Optional) authentication.getCredentials();

        if (!username.isPresent() || !password.isPresent()) {
            throw new BadCredentialsException("Invalid User Credentials");
        }

        User user = userService.findUserByUserName(username.get());
        if (user == null) {
            throw new BadCredentialsException("User not found.");
        }

        if (!encoder.matches(password.get(), user.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }

        AuthenticationWithToken authenticationWithToken = new AuthenticationWithToken(user, null, user.getAuthorities());
        String newToken = tokenService.generateNewToken();
        authenticationWithToken.setToken(newToken);
        tokenService.store(newToken, authenticationWithToken);

        return authenticationWithToken;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
