package fit.tdc.projectjava02.DemoProjectJava02.controller.user;

import fit.tdc.projectjava02.DemoProjectJava02.model.CategoryModel;
import fit.tdc.projectjava02.DemoProjectJava02.model.ProductModel;
import fit.tdc.projectjava02.DemoProjectJava02.service.CategoryService;
import fit.tdc.projectjava02.DemoProjectJava02.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/index/category")
public class CategoryControllerUser {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String list(Model model) {
        List<CategoryModel> categories = categoryService.getAll();
        model.addAttribute("categories", categories);
        return "user/category_list"; // dẫn tới category-list.html như mẫu trên
    }

    // Hiển thị sản phẩm theo danh mục
    @GetMapping("/product/{categoryId}")
    public String listByCategory(@PathVariable("categoryId") Long categoryId,
                                 @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                 Model model) {

        Page<ProductModel> products = productService.findByCategoryId(categoryId, pageNo);
        model.addAttribute("products", products);
        model.addAttribute("category", categoryService.findById(categoryId));
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("categoryId", categoryId);
        return "user/category_product";
    }
}
