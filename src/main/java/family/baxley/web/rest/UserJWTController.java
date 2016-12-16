package family.baxley.web.rest;

import java.util.Collections;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import family.baxley.security.jwt.JWTConfigurer;
import family.baxley.security.jwt.TokenProvider;
import family.baxley.web.rest.vm.LoginVM;

@RestController
@RequestMapping("/api")
public class UserJWTController {
	 private final Logger log = LoggerFactory.getLogger(UserJWTController.class);
    @Inject
    private TokenProvider tokenProvider;

    @Inject
    private AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    @Timed
    public ResponseEntity<?> authorize(@Valid @RequestBody LoginVM loginVM, HttpServletResponse response) {
    	log.info("loginVM.getUsername()="+loginVM.getUsername());
    	
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), "");
//TestingAuthenticationToken authenticationToken =
//new TestingAuthenticationToken(loginVM.getUsername(), "");
//authenticationToken.setAuthenticated(true);
        try {
        	log.info("authenticationManager="+authenticationManager);
        	log.info("authenticationToken="+authenticationToken);
            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        	log.info("Authentication="+authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.createToken(authentication, false);
            response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
            return ResponseEntity.ok(new JWTToken(jwt));
        } catch (AuthenticationException exception) {
        	log.error("authorize exception"+exception);
            return new ResponseEntity<>(Collections.singletonMap("AuthenticationException",exception.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
        }
    }
}
