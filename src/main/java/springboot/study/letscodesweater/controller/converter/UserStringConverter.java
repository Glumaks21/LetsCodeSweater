package springboot.study.letscodesweater.controller.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import springboot.study.letscodesweater.domain.User;
import springboot.study.letscodesweater.repos.UserRepo;

@Component
@RequiredArgsConstructor
public class UserStringConverter implements Converter<String, User> {

    private final UserRepo repo;

    @Override
    public User convert(String username) {
        return repo.findByUsername(username).orElse(null);
    }

}
