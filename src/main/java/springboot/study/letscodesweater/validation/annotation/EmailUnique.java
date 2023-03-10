package springboot.study.letscodesweater.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import springboot.study.letscodesweater.validation.validator.EmailUniqueValidator;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = EmailUniqueValidator.class)
@Documented
public @interface EmailUnique {
    String message() default "Email is already registered";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
