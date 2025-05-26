package fit.tdc.projectjava02.DemoProjectJava02.repository;

import fit.tdc.projectjava02.DemoProjectJava02.model.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    @Query("SELECT p FROM ProductModel p WHERE p.name LIKE %?1% ")
    List<ProductModel> searchProduct(String keyword);
    Page<ProductModel> findByCategory_CategoryId(Long categoryId, Pageable pageable);

}
