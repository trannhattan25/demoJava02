package fit.tdc.projectjava02.DemoProjectJava02.service;

import fit.tdc.projectjava02.DemoProjectJava02.model.CategoryModel;
import org.hibernate.Internal;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    List<CategoryModel> getAll();

    Boolean create(CategoryModel category);

    CategoryModel findById(Long id);

    Boolean update(CategoryModel category);

    Boolean delete(Long id);

    List<CategoryModel> searchCategory(String keyword);

    Page<CategoryModel> searchCategory(String keyword, Integer pageNo);


    Page<CategoryModel> getAll(Integer pageNo);

}