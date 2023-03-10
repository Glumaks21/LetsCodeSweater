package springboot.study.letscodesweater.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import springboot.study.letscodesweater.service.FileStorageService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private final String uploadPath;

    public FileStorageServiceImpl(@Value("${upload.path}") String uploadPath) {
        this.uploadPath = uploadPath;
    }

    @Override
    public String store(MultipartFile file) {
        Path path = Path.of(uploadPath);
        try {
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }

            String uuid = UUID.randomUUID().toString();
            String resultFileName = uuid + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + File.separator + resultFileName));
            return resultFileName;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
