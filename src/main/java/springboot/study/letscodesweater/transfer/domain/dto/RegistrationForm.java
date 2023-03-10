package springboot.study.letscodesweater.transfer.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import springboot.study.letscodesweater.validation.annotation.Recaptcha;
import springboot.study.letscodesweater.validation.annotation.EmailUnique;
import springboot.study.letscodesweater.validation.annotation.PasswordMatch;
import springboot.study.letscodesweater.validation.annotation.UsernameUnique;

@Getter
@Setter
@ToString
@PasswordMatch
public class RegistrationForm {
    @Length(min = 6, max = 20, message = "Username length must be between 6 and 20")
    @Pattern(regexp = "[a-zA-z][A-za-z0-9]{5,19}",
            message = "Username must starts with letter and consist of letters and numbers")
    @UsernameUnique
    private String username;

    @Email(message = "Email is not correct")
    @NotBlank(message = "Email cannot be empty")
    @EmailUnique
    private String email;

    @Length(min = 6, max = 20,
            message = "Password length must be between 6 - 20")
    private String password;

    @Length(min = 6, max = 20,
            message = "Password confirmation length must be between 6 - 20")
    private String passwordConfirmation;

    @Recaptcha
    private String recaptchaResponse;
}
