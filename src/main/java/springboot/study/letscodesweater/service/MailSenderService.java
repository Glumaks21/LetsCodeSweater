package springboot.study.letscodesweater.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderService {
    private final MailSender sender;

    public void send(String emailTo, String subject, String text) {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setFrom("");
        emailMessage.setTo(emailTo);
        emailMessage.setSubject(subject);
        emailMessage.setText(text);
        sender.send(emailMessage);
    }
}
