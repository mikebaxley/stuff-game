package family.baxley.web.rest.vm;

import family.baxley.config.Constants;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * View Model object for storing a user's credentials.
 */
public class LoginVM {

    @Pattern(regexp = Constants.LOGIN_REGEX)
    @NotNull
    @Size(min = 1, max = 50)
    private String username;





    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }





    @Override
    public String toString() {
        return "LoginVM{" +
            ", username='" + username + '\'' +
            '}';
    }
}
