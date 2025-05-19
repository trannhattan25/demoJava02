package fit.tdc.projectjava02.DemoProjectJava02.service;


import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    void store(MultipartFile file);
    void store(MultipartFile file, String filename);
}
