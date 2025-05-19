package fit.tdc.projectjava02.DemoProjectJava02.service;

import fit.tdc.projectjava02.DemoProjectJava02.model.CategoryModel;
import fit.tdc.projectjava02.DemoProjectJava02.model.ProductModel;
import fit.tdc.projectjava02.DemoProjectJava02.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    List<ProductModel> getAll();

    Boolean create(ProductModel product);

    ProductModel findById(Long id);

    Boolean update(ProductModel product);

    Boolean delete(Long id);
    List<ProductModel> searchProduct(String keyword);

    Page<ProductModel> searchProduct(String keyword, Integer pageNo);

    Page<ProductModel> getAll(Integer pageNo);

}
