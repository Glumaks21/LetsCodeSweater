package springboot.study.letscodesweater.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String store(MultipartFile file);
}
