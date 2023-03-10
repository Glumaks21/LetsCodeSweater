package springboot.study.letscodesweater.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import springboot.study.letscodesweater.transfer.json.CaptchaResponseDto;
import springboot.study.letscodesweater.validation.annotation.Recaptcha;

import java.util.Collections;

public class RecaptchaValidator implements ConstraintValidator<Recaptcha, String> {
    private static final String RECAPTCHA_URL =
            "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    private final RestTemplate restTemplate;
    private final String recaptchaSecret;

    @Autowired
    public RecaptchaValidator(RestTemplate restTemplate,
                              @Value("${recaptcha.secret}") String recaptchaSecret) {
        this.restTemplate = restTemplate;
        this.recaptchaSecret = recaptchaSecret;
    }

    @Override
    public boolean isValid(String recaptchaResponse, ConstraintValidatorContext constraintValidatorContext) {
        String uri = RECAPTCHA_URL.formatted(recaptchaSecret, recaptchaResponse);
        CaptchaResponseDto validatedRecaptchaResponse =
                restTemplate.postForObject(uri, Collections.emptyList(), CaptchaResponseDto.class);
        return validatedRecaptchaResponse != null && validatedRecaptchaResponse.isSuccess();
    }
}
