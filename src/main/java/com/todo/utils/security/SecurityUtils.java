package com.todo.utils.security;

import com.todo.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

import static com.todo.utils.Constants.BASE_URL;
import static com.todo.utils.Constants.X_TOKEN;

/**
 * Created by Snayki on 17.03.2016.
 */
public class SecurityUtils {
    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise
     */
    public static boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Collection<? extends GrantedAuthority> authorities = securityContext.getAuthentication().getAuthorities();
        return authorities != null;
    }

    public static User getAuthenticatedUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        return (User) authentication.getPrincipal();
    }

    public static void createCookie(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String token) {
        Cookie cookie = WebUtils.getCookie(httpRequest, X_TOKEN);
        if (cookie==null || token!=null && !token.equals(cookie.getValue())) {
            cookie = new Cookie(X_TOKEN, token);
            cookie.setMaxAge(-1);
            cookie.setPath(BASE_URL);
            httpResponse.addCookie(cookie);
        }
    }

    public static void deleteCookie(HttpServletResponse httpResponse) {
        Cookie cookie = new Cookie(X_TOKEN, "");
        cookie.setMaxAge(0);
        cookie.setPath(BASE_URL);
        httpResponse.addCookie(cookie);
    }
}
