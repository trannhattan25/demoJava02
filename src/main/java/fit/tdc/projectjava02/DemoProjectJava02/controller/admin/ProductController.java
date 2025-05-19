package fit.tdc.projectjava02.DemoProjectJava02.controller.admin;

import fit.tdc.projectjava02.DemoProjectJava02.model.CategoryModel;
import fit.tdc.projectjava02.DemoProjectJava02.model.ProductModel;
import fit.tdc.projectjava02.DemoProjectJava02.service.CategoryService;
import fit.tdc.projectjava02.DemoProjectJava02.service.ProductService;
import fit.tdc.projectjava02.DemoProjectJava02.service.StorageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private StorageService storageService;

    // trang chủ
    @GetMapping("/")
    public String dashboard(Model model,@Param("keyword")String keyword,@RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo) {
        Page<ProductModel> listCategories = this.productService.getAll(pageNo);

        if (keyword != null) {
            listCategories = this.productService.searchProduct(keyword, pageNo);
            model.addAttribute("keyword", keyword);
        }
        model.addAttribute("totalPages",listCategories.getTotalPages());
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("categories",listCategories  );

        return "admin/product/list";
    }

    // Thêm
    @GetMapping("/create")
    public String showAddForm(Model model) {
        model.addAttribute("product", new ProductModel());
        List<CategoryModel> listCate = this.categoryService.getAll();
        model.addAttribute("listCate", listCate);

        return "admin/product/form";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") @Valid ProductModel product,
                              BindingResult result,
                              @RequestParam("productImage") MultipartFile file,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("listCate", categoryService.getAll());
            return "admin/product/form";
        }

        // Nếu là update (id != null), lấy ảnh cũ
        if (product.getId() != null) {
            ProductModel existing = productService.findById(product.getId());
            if (existing != null && file.isEmpty()) {
                product.setImageUrl(existing.getImageUrl()); // Giữ ảnh cũ
            }
        }

        if (!file.isEmpty()) {
            String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            try {
                storageService.store(file, filename); // Bạn cần xử lý lỗi trong store()
                product.setImageUrl(filename);
            } catch (RuntimeException e) {
                model.addAttribute("uploadError", "Không thể lưu file: " + e.getMessage());
                model.addAttribute("listCate", categoryService.getAll());
                return "admin/product/form";
            }
        }

        productService.create(product); // Có thể là create/update đều dùng chung
        return "redirect:/admin/products/";
    }

    //Sửa
    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        ProductModel product = this.productService.findById(id);
        List<CategoryModel> listCate = this.categoryService.getAll();
        model.addAttribute("listCate", listCate);
        model.addAttribute("product", product);
        return "admin/product/form";
    }
    @PostMapping("/edit-product")
    public String update(@ModelAttribute("product") @Valid ProductModel product,
                         BindingResult result,
                         @RequestParam("productImage") MultipartFile file,
                         Model model) {

        if (result.hasErrors()) {
            model.addAttribute("listCate", categoryService.getAll());
            return "admin/product/form";
        }

        // Lấy thông tin cũ từ DB
        ProductModel oldProduct = productService.findById(product.getId());

        if (!file.isEmpty()) {
            // Nếu người dùng chọn ảnh mới, thì lưu ảnh mới
            String originalFileName = file.getOriginalFilename();
            String extension = originalFileName != null && originalFileName.contains(".")
                    ? originalFileName.substring(originalFileName.lastIndexOf("."))
                    : "";
            String newFileName = UUID.randomUUID().toString() + extension;

            storageService.store(file, newFileName);
            product.setImageUrl(newFileName);
        } else {
            // Nếu không chọn ảnh mới, giữ lại ảnh cũ
            product.setImageUrl(oldProduct.getImageUrl());
        }

        if (productService.create(product)) {
            return "redirect:/admin/products/";
        }

        model.addAttribute("listCate", categoryService.getAll());
        return "admin/product/form";
    }




    //Xóa
    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        productService.delete(id);
        return "redirect:/admin/products/";
    }

}
