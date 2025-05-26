package fit.tdc.projectjava02.DemoProjectJava02.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
@Service
public class CloudinaryStorageService implements StorageService {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String store(MultipartFile file) {
        try {
            Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return result.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException("Upload thất bại", e);
        }
    }

    @Override
    public String store(MultipartFile file, String filename) {
        try {
            Map result = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("public_id", filename));
            return result.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException("Upload thất bại", e);
        }
    }
}
