package family.baxley.web.rest.vm;

import family.baxley.config.Constants;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * View Model object for the stuff home page options
 */
public class CreateGameVM {

    @Pattern(regexp = Constants.LOGIN_REGEX)
    @NotNull
    @Size(min = 1, max = 50)
    private String gameName;







    @Override
    public String toString() {
        return "CreateGameVM{" +
            ", username='" + gameName + '\'' +
            '}';
    }
}
