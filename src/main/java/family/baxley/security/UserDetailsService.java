package family.baxley.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

//    @Inject
//    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);
        
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (StringUtils.equalsIgnoreCase("admin", login)) {
        	grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
        	grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(login, "", grantedAuthorities);
        log.debug("userDetails="+ userDetails);
        return userDetails;
//        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
//        Optional<User> userFromDatabase = userRepository.findOneByLogin(lowercaseLogin);
//        return userFromDatabase.map(user -> {
//            if (!user.getActivated()) {
//                throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
//            }
//            List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
//                    .map(authority -> new SimpleGrantedAuthority(authority.getName()))
//                .collect(Collectors.toList());
//            return new org.springframework.security.core.userdetails.User(lowercaseLogin,
//                user.getPassword(),
//                grantedAuthorities);
//        }).orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the " +
//        "database"));
    }
}
