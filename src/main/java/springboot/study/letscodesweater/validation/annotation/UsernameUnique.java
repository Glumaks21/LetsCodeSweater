package springboot.study.letscodesweater.validation.annotation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import springboot.study.letscodesweater.validation.validator.EmailUniqueValidator;
import springboot.study.letscodesweater.validation.validator.UsernameUniqueValidator;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UsernameUniqueValidator.class)
@Documented
public @interface UsernameUnique {
    String message() default "Username is already registered";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
