package springboot.study.letscodesweater.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import springboot.study.letscodesweater.domain.Message;
import springboot.study.letscodesweater.domain.User;

public interface MessageService {
    Page<Message> getMessages(Pageable pageable, String filter);
    Page<Message> getUserMessages(User user, Pageable pageable);
    void postMessage(Message message, MultipartFile file);
}
