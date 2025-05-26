package fit.tdc.projectjava02.DemoProjectJava02.service;


import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    // Trả về URL của file vừa upload
    String store(MultipartFile file);
    String store(MultipartFile file, String filename);
}
