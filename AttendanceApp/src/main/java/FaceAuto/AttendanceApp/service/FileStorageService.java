package FaceAuto.AttendanceApp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    @Value("${file.external-upload-dir}") // application.properties에서 설정한 디렉토리 경로
    private String externalUploadDir;

    public String storeFile(MultipartFile file, String fileName) throws IOException {
        Path uploadPath = Path.of(externalUploadDir).toAbsolutePath().normalize();

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

        String storedFileName = fileName + fileExtension;

        Path storedFilePath = uploadPath.resolve(storedFileName);
        Files.copy(file.getInputStream(), storedFilePath, StandardCopyOption.REPLACE_EXISTING);

        return storedFileName;
    }

    public Path loadFile(String fileName) {
        return Path.of(externalUploadDir).resolve(fileName);
    }
}
