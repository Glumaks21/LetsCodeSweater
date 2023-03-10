package springboot.study.letscodesweater.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import springboot.study.letscodesweater.domain.Message;
import springboot.study.letscodesweater.domain.User;
import springboot.study.letscodesweater.repos.MessageRepo;
import springboot.study.letscodesweater.service.FileStorageService;
import springboot.study.letscodesweater.service.MessageService;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepo messageRepo;
    private final FileStorageService storageService;

    @Autowired
    public MessageServiceImpl(MessageRepo messageRepo, FileStorageService storageService) {
        this.messageRepo = messageRepo;
        this.storageService = storageService;
    }

    @Override
    public Page<Message> getMessages(Pageable pageable, String filter) {
        return StringUtils.hasText(filter)?
                messageRepo.findByTag(filter, pageable):
                messageRepo.findAll(pageable);
    }

    @Override
    public Page<Message> getUserMessages(User user, Pageable pageable) {
        return messageRepo.findByAuthor(user, pageable);
    }

    @Override
    public void postMessage(Message message, MultipartFile file) {
        if (file != null && StringUtils.hasText(file.getOriginalFilename())) {
            message.setFilename(storageService.store(file));
        }
        messageRepo.save(message);
    }
}
