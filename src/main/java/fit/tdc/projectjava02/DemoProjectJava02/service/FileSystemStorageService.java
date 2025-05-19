package fit.tdc.projectjava02.DemoProjectJava02.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

@Service  // <-- Quan trọng!
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation = Paths.get("uploads");

    @Override
    public void store(MultipartFile file) {
        try {
            Files.createDirectories(rootLocation); // Tạo thư mục nếu chưa có
            Path destinationFile = rootLocation.resolve(
                            Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();

            // Kiểm tra file không nằm ngoài thư mục uploads
            if (!destinationFile.getParent().equals(rootLocation.toAbsolutePath())) {
                throw new RuntimeException("Không thể lưu file ra ngoài thư mục uploads.");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }


    public void store(MultipartFile file, String filename) {
        if (file.isEmpty()) {
            throw new RuntimeException("File rỗng, không thể lưu.");
        }

        try {
            Path destinationFile = rootLocation.resolve(Paths.get(filename)).normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(rootLocation.toAbsolutePath())) {
                throw new RuntimeException("Không thể lưu file ra ngoài thư mục uploads.");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (IOException e) {
            throw new RuntimeException("Gặp lỗi khi lưu file: " + e.getMessage(), e);
        }
    }

}
