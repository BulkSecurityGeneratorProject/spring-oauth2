package lejendary.oauth2.security;

import lejendary.oauth2.domain.User;
import lejendary.oauth2.util.AppConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Utility class for Spring Security.
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user
     */
    public static User getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof User) {
                return (User) authentication.getPrincipal();
            }
        }

        return new User();
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise
     */
    public static boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Collection<? extends GrantedAuthority> authorities = securityContext.getAuthentication().getAuthorities();
        if (authorities != null && authorities.isEmpty()) {
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(AppConstants.Authorities.ANONYMOUS)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * If the current user has a specific authority (security role).
     * <p>
     * <p>The name of this method comes from the isUserInRole() method in the Servlet API</p>
     *
     * @param authority the authorithy to check
     * @return true if the current user has the authority, false otherwise
     */
    public static boolean isCurrentUserInRole(String authority) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null && authority != null) {
            if (authentication.getPrincipal() instanceof User) {
                User springSecurityUser = (User) authentication.getPrincipal();
                for (String a : springSecurityUser.getAuthority()) {
                    if (a.equals(authority)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isCurrentUserInRoles(String... authority) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null && authority != null) {
            if (authentication.getPrincipal() instanceof User) {
                List<String> authorityList = Arrays.asList(authority);
                User springSecurityUser = (User) authentication.getPrincipal();
                for (String a : springSecurityUser.getAuthority()) {
                    if (authorityList.contains(a)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
