package by.hackaton.bookcrossing.util;

import by.hackaton.bookcrossing.configuration.oauth.CustomOAuth2User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class AuthUtils {

    public static String getEmailFromAuth(Authentication auth) {
        if (auth instanceof UsernamePasswordAuthenticationToken)
            return auth.getPrincipal().toString();
        return ((CustomOAuth2User) auth.getPrincipal()).getEmail();
    }
}
