package springboot.study.letscodesweater.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import springboot.study.letscodesweater.repos.UserRepo;
import springboot.study.letscodesweater.validation.annotation.UsernameUnique;

public class UsernameUniqueValidator implements ConstraintValidator<UsernameUnique, String> {
    private final UserRepo userRepo;

    @Autowired
    public UsernameUniqueValidator(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return !userRepo.existsByUsername(username);
    }
}
