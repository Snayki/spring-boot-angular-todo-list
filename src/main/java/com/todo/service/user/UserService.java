package com.todo.service.user;

import com.todo.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by Snayki on 18.03.2016.
 */
public interface UserService extends UserDetailsService {
    User findUserByUserName(String username);

    User create(User user);
}
