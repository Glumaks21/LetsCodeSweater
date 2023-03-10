package springboot.study.letscodesweater.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import springboot.study.letscodesweater.repos.UserRepo;
import springboot.study.letscodesweater.validation.annotation.EmailUnique;

public class EmailUniqueValidator implements ConstraintValidator<EmailUnique, String> {
    private final UserRepo userRepo;

    @Autowired
    public EmailUniqueValidator(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !userRepo.existsByEmail(email);
    }
}
