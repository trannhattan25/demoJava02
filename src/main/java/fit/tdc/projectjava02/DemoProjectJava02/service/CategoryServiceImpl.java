package fit.tdc.projectjava02.DemoProjectJava02.service;

import fit.tdc.projectjava02.DemoProjectJava02.model.CategoryModel;
import fit.tdc.projectjava02.DemoProjectJava02.repository.CategoryRepository;
import org.hibernate.Internal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public List<CategoryModel> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Boolean create(CategoryModel category) {
        try {
            this.categoryRepository.save(category);
            return true;
        } catch (Exception e) {
           e.printStackTrace();
        }
        return false;

    }

    @Override
    public CategoryModel findById(Long id) {

        return this.categoryRepository.findById(id).get();
    }

    @Override
    public Boolean update(CategoryModel category) {

        try {
             this.categoryRepository.save( category);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean delete(Long id) {

        try {
            this.categoryRepository.deleteById( id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<CategoryModel> searchCategory(String keyword) {
        return this.categoryRepository.searchCategory(keyword);
    }

    @Override
    public Page<CategoryModel> searchCategory(String keyword, Integer pageNo) {
        List list = this.searchCategory(keyword);
        Pageable pageable = PageRequest.of(pageNo-1,2);

        Integer  start =(int) pageable.getOffset();
        Integer  end =(int)((pageable.getOffset()+ pageable.getPageSize() ) > list.size()?list.size():pageable.getOffset()+ pageable.getPageSize());

        list = list.subList(start,end);
        return new PageImpl<CategoryModel>(list,pageable,this.searchCategory(keyword).size());

    }

    @Override
    public Page<CategoryModel> getAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1,3);
        return this.categoryRepository.findAll(pageable);
    }
}