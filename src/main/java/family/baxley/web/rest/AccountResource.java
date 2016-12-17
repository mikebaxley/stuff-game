package family.baxley.web.rest;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import family.baxley.security.AuthoritiesConstants;
import family.baxley.service.dto.UserDTO;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);





    /**
     * GET  /authenticate : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request
     * @return the login if the user is authenticated
     */
    @GetMapping("/authenticate")
    @Timed
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * GET  /account : get the current user.
     *
     * @return the ResponseEntity with status 200 (OK) and the current user in body, or status 500 (Internal Server Error) if the user couldn't be returned
     */
    @GetMapping("/account")
    @Timed
    public ResponseEntity<UserDTO> getAccount(HttpServletRequest request) {
    	UserDTO user = new UserDTO(request.getRemoteUser());
    	if (StringUtils.equalsIgnoreCase("admin",user.getLogin()) ) {
    		user.getAuthorities().add(AuthoritiesConstants.ADMIN);
    	} else {
    		user.getAuthorities().add(AuthoritiesConstants.USER);
    	}
    	
    	
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

//    /**
//     * POST  /account : update the current user information.
//     *
//     * @param userDTO the current user information
//     * @return the ResponseEntity with status 200 (OK), or status 400 (Bad Request) or 500 (Internal Server Error) if the user couldn't be updated
//     */
//    @PostMapping("/account")
//    @Timed
//    public ResponseEntity<String> saveAccount(@Valid @RequestBody UserDTO userDTO) {
//        Optional<User> existingUser = userRepository.findOneByEmail(userDTO.getEmail());
//        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userDTO.getLogin()))) {
//            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("user-management", "emailexists", "Email already in use")).body(null);
//        }
//        return userRepository
//            .findOneByLogin(SecurityUtils.getCurrentUserLogin())
//            .map(u -> {
//                userService.updateUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
//                    userDTO.getLangKey());
//                return new ResponseEntity<String>(HttpStatus.OK);
//            })
//            .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
//    }

 
}