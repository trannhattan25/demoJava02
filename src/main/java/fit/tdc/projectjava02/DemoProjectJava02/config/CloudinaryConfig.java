package fit.tdc.projectjava02.DemoProjectJava02.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = ObjectUtils.asMap(
                "cloud_name", "dyx0i1dga",
                "api_key", "428555769368546",
                "api_secret", "xWSLTI9fl8gJQgx7IouX0BzpExc",
                "secure", "true"
        );
        return new Cloudinary(config);
    }
}
