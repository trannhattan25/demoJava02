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
@RequestMapping("/admin/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private StorageService storageService;

    // Trang danh sách sản phẩm
    @GetMapping("/")
    public String dashboard(Model model, @Param("keyword") String keyword,
                            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
        Page<ProductModel> listProducts = this.productService.getAll(pageNo);

        if (keyword != null) {
            listProducts = this.productService.searchProduct(keyword, pageNo);
            model.addAttribute("keyword", keyword);
        }

        model.addAttribute("totalPages", listProducts.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("categories", listProducts); // Tên `categories` đã dùng trong view

        return "admin/product/list";
    }

    // Hiển thị form thêm sản phẩm
    @GetMapping("/create")
    public String showAddForm(Model model) {
        model.addAttribute("product", new ProductModel());
        model.addAttribute("listCate", categoryService.getAll());
        return "admin/product/form";
    }

    // Lưu sản phẩm mới
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") @Valid ProductModel product,
                              BindingResult result,
                              @RequestParam("productImage") MultipartFile file,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("listCate", categoryService.getAll());
            return "admin/product/form";
        }

        if (product.getId() != null) {
            ProductModel existing = productService.findById(product.getId());
            if (existing != null && file.isEmpty()) {
                product.setImageUrl(existing.getImageUrl());
            }
        }

        if (!file.isEmpty()) {
            String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            try {
                storageService.store(file, filename);
                product.setImageUrl(filename);
            } catch (RuntimeException e) {
                model.addAttribute("uploadError", "Không thể lưu file: " + e.getMessage());
                model.addAttribute("listCate", categoryService.getAll());
                return "admin/product/form";
            }
        }

        productService.create(product);
        return "redirect:/admin/product/";
    }

    // Hiển thị form sửa sản phẩm
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        ProductModel product = productService.findById(id);
        model.addAttribute("product", product);
        model.addAttribute("listCate", categoryService.getAll());
        return "admin/product/form";
    }

    // Cập nhật sản phẩm
    @PostMapping("/update")
    public String update(@ModelAttribute("product") @Valid ProductModel product,
                         BindingResult result,
                         @RequestParam("productImage") MultipartFile file,
                         Model model) {

        if (result.hasErrors()) {
            model.addAttribute("listCate", categoryService.getAll());
            return "admin/product/form";
        }

        ProductModel oldProduct = productService.findById(product.getId());

        if (!file.isEmpty()) {
            String originalFileName = file.getOriginalFilename();
            String extension = originalFileName != null && originalFileName.contains(".")
                    ? originalFileName.substring(originalFileName.lastIndexOf("."))
                    : "";
            String newFileName = UUID.randomUUID().toString() + extension;

            storageService.store(file, newFileName);
            product.setImageUrl(newFileName);
        } else {
            product.setImageUrl(oldProduct.getImageUrl());
        }

        productService.create(product);
        return "redirect:/admin/product/";
    }

    // Xóa sản phẩm
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        productService.delete(id);
        return "redirect:/admin/product/";
    }
}
