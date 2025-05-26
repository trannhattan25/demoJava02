package fit.tdc.projectjava02.DemoProjectJava02.controller;

import fit.tdc.projectjava02.DemoProjectJava02.model.CategoryModel;
import fit.tdc.projectjava02.DemoProjectJava02.model.ProductModel;
import fit.tdc.projectjava02.DemoProjectJava02.service.CategoryService;
import fit.tdc.projectjava02.DemoProjectJava02.service.ProductService;
import fit.tdc.projectjava02.DemoProjectJava02.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ProductService productService;

    @GetMapping({"/index", "/"})
    public String homePage(Model model,
                           @Param("keyword") String keyword,
                           @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {

        Page<ProductModel> productPage = productService.getAll(pageNo);


        if (keyword != null && !keyword.isEmpty()) {
            productPage = productService.searchProduct(keyword, pageNo);
            model.addAttribute("keyword", keyword);
        }


        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("products", productPage);

        return "index"; // Đi tới home/index.html
    }

    @GetMapping("/detail")
    public String productDetail(@RequestParam("id") Long id, Model model) {
        ProductModel product = productService.findById(id);
        if (product == null) {
            return "redirect:/index"; // Nếu không tìm thấy sản phẩm thì về trang chủ
        }

        model.addAttribute("product", product);
        return "user/detal_product"; // Trả về file product-detail.html trong templates
    }
}
