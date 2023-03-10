package springboot.study.letscodesweater.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import springboot.study.letscodesweater.validation.validator.PasswordMatchValidator;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = PasswordMatchValidator.class)
@Documented
public @interface PasswordMatch {
    String message() default "Password doesn't match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
