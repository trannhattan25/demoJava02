    package fit.tdc.projectjava02.DemoProjectJava02.service;

    import fit.tdc.projectjava02.DemoProjectJava02.model.ProductModel;
    import fit.tdc.projectjava02.DemoProjectJava02.repository.ProductRepository;
    import org.springframework.beans.factory.annotation.Autowired;

    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.PageImpl;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.data.domain.Pageable;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    public class ProductServiceImpl implements ProductService {
        @Autowired
        private ProductRepository productRepository;


        @Override
        public List<ProductModel> getAll() {
            return productRepository.findAll();
        }

        @Override
        public Boolean create(ProductModel product) {
            try {
                this.productRepository.save(product);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;

        }

        @Override
        public ProductModel findById(Long id) {

            return this.productRepository.findById(id).orElse(null);
        }

        @Override
        public Boolean update(ProductModel product) {

            try {
                this.productRepository.save(product);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public Boolean delete(Long id) {

            try {
                this.productRepository.deleteById(id);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public List<ProductModel> searchProduct(String keyword) {
            return this.productRepository.searchProduct(keyword);

        }

        @Override
        public Page<ProductModel> searchProduct(String keyword, Integer pageNo) {
            List list = this.searchProduct(keyword);
            Pageable pageable = PageRequest.of(pageNo - 1, 10);

            Integer start = (int) pageable.getOffset();
            Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size() : pageable.getOffset() + pageable.getPageSize());

            list = list.subList(start, end);
            return new PageImpl<ProductModel>(list, pageable, this.searchProduct(keyword).size());

        }

        @Override
        public Page<ProductModel> getAll(Integer pageNo) {
            Pageable pageable = PageRequest.of(pageNo - 1, 10);
            return this.productRepository.findAll(pageable);
        }

        @Override
        public Page<ProductModel> findByCategoryId(Long categoryId, int pageNo) {
            int pageSize = 9;
            Pageable pageable = PageRequest.of(pageNo - 1, pageSize);


            return productRepository.findByCategory_CategoryId(categoryId, pageable);
        }
    }
