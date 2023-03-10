package springboot.study.letscodesweater.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import springboot.study.letscodesweater.transfer.domain.dto.RegistrationForm;
import springboot.study.letscodesweater.validation.annotation.PasswordMatch;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, RegistrationForm> {
    @Override
    public boolean isValid(RegistrationForm registrationForm,
                           ConstraintValidatorContext constraintValidatorContext) {
        String password = registrationForm.getPassword();
        String passwordConfirmation = registrationForm.getPasswordConfirmation();
        return password != null && password.equals(passwordConfirmation);
    }
}
