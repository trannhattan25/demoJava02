package fit.tdc.projectjava02.DemoProjectJava02.repository;

import fit.tdc.projectjava02.DemoProjectJava02.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {

    @Query("SELECT c FROM CategoryModel c WHERE c.name LIKE %?1% ")
    List<CategoryModel> searchCategory(String keyword);
}
