package springboot.study.letscodesweater.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import springboot.study.letscodesweater.validation.validator.RecaptchaValidator;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = RecaptchaValidator.class)
@Documented
public @interface Recaptcha {
    String message() default "Recaptcha is not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
