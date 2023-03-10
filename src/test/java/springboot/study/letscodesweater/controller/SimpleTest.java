package springboot.study.letscodesweater.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import springboot.study.letscodesweater.domain.User;
import springboot.study.letscodesweater.repos.UserRepo;
import springboot.study.letscodesweater.service.UserService;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest()
public class SimpleTest {
    @MockBean
    UserRepo userRepo;

    @MockBean
    PasswordEncoder passwordEncoder;

    @MockBean
    MailSender mailSender;

    @Autowired
    UserService userService;

    @ParameterizedTest
    @MethodSource("correctUsers")
    void addUser(String username, String email, String password) {
        userService.registerUser(username, email, password);

        Mockito.verify(passwordEncoder, Mockito.times(1))
                .encode(ArgumentMatchers.any());
        Mockito.verify(userRepo, Mockito.times(1))
                .save(ArgumentMatchers.argThat(
                        user -> user.getUsername().equals(username) &&
                                user.getEmail().equals(email))
                );
        Mockito.verify(mailSender, Mockito.never()).send();
    }

    static Stream<Arguments> correctUsers() {
        return Stream.of(
                Arguments.of("username", "email@ukr.ua", "123456"),
                Arguments.of("billy", "my@ukr.cum", "7ewtwq3432ewr"),
                Arguments.of("jonny", "jonny@ukr.cum", "7ewtwq3432ewr")
        );
    }

    @Test
    void activateUser() {
        String activationCode = "activate";
        User user = new User();
        user.setActivationCode(activationCode);

        Mockito.doReturn(Optional.of(user))
                .when(userRepo)
                .findByActivationCode(activationCode);

        boolean isActivated = userService.activateUser(activationCode);

        assertThat(isActivated).isTrue();
        assertThat(user.getActivationCode()).isNull();
        assertThat(user.isEnabled()).isTrue();

        Mockito.verify(userRepo, Mockito.times(1))
                .findByActivationCode(activationCode);
        Mockito.verify(userRepo, Mockito.times(1))
                .save(user);
    }

    @Test
    void activateFailUser() {
        String activationCode = "activate";
        boolean isActivated = userService.activateUser(activationCode);

        assertThat(isActivated).isFalse();

        Mockito.verify(userRepo, Mockito.times(1))
                .findByActivationCode(activationCode);
        Mockito.verify(userRepo, Mockito.never())
                .save(Mockito.any(User.class));
    }
}
