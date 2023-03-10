package springboot.study.letscodesweater.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import springboot.study.letscodesweater.domain.Role;
import springboot.study.letscodesweater.domain.User;
import springboot.study.letscodesweater.repos.UserRepo;
import springboot.study.letscodesweater.service.MailSenderService;
import springboot.study.letscodesweater.service.UserService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo repo;
    private final MailSenderService mailSender;
    private final PasswordEncoder passwordEncoder;

    @Value("${context.path}")
    private String contextPath;

    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return repo.findByUsername(username);
    }

    @Override
    public void registerUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setRoles(Collections.singleton(Role.USER));
        user.setActive(true);
        repo.save(user);
//        sendActivationCodeMessage(user);
    }

    @Override
    public boolean activateUser(String activationCode) {
        Optional<User> userCandidate = repo.findByActivationCode(activationCode);
        if (userCandidate.isEmpty()) {
            return false;
        }

        User user = userCandidate.get();
        user.setActivationCode(null);
        repo.save(user);
        return true;
    }

    @Override
    public void editUser(User user, String email, String password) {
        if (StringUtils.hasText(email) && !user.getEmail().equals(email)) {
            user.setEmail(email);
            user.setActivationCode(UUID.randomUUID().toString());
        }

        if (StringUtils.hasText(password)) {
            user.setPasswordHash(passwordEncoder.encode(password));
        }

        repo.save(user);
//        sendActivationCodeMessage(user);
    }

    @Override
    public void updateUser(User user) {
        if (!repo.existsById(user.getId())) {
            throw new IllegalArgumentException();
        }
        repo.save(user);
    }

    @Override
    public void subscribe(User channel, User newSubscriber) {
       channel.getSubscribers().add(newSubscriber);
        repo.save(channel);
    }

    @Override
    public void unsubscribe(User channel, User newSubscriber) {
        channel.getSubscribers().remove(newSubscriber);
        repo.save(channel);
    }


    private void sendActivationCodeMessage(User user) {
        String email = user.getEmail();
        String activationCode = user.getActivationCode();
        if (email == null || email.isBlank() ||
                activationCode == null || activationCode.isBlank()) {
            return;
        }

        String message = String.format(
                "Hello, %s\n" +
                        "Welcome to Sweater. Please, visit next link to activate account: " +
                        "%sactivate/%s",
                contextPath,
                user.getUsername(),
                activationCode
        );
        mailSender.send(email, "Activation code", message);
    }

    @Override
    public Long getSubscribersCountById(Long id) {
        return repo.countSubscribersById(id);
    }

    @Override
    public Long getSubscriptionsCountById(Long id) {
        return repo.countSubscriptionsById(id);
    }

}
