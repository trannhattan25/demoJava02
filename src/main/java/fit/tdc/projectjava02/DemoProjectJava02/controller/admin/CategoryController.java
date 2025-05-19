package fit.tdc.projectjava02.DemoProjectJava02.controller.admin;

import fit.tdc.projectjava02.DemoProjectJava02.model.CategoryModel;
import fit.tdc.projectjava02.DemoProjectJava02.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("admin/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //Lấy
    @GetMapping("/")
    public String list(Model model, @Param("keyword") String keyword, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {


        Page<CategoryModel> list = this.categoryService.getAll(pageNo);

        if (keyword != null) {
            list = this.categoryService.searchCategory(keyword, pageNo);
            model.addAttribute("keyword", keyword);
        }
        model.addAttribute("totalPages", list.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("categories", list);
        return "admin/category/list";
    }

    //Thêm
    @GetMapping("/create")
    public String create(Model model) {
        CategoryModel category = new CategoryModel();
        model.addAttribute("category", category);
        return "admin/category/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("category") @Valid CategoryModel category,
                       BindingResult result, Model model) {
        if (this.categoryService.create(category)) {
            return "redirect:/admin/category/";

        }
        return "admin/category/form";

    }

    //Sửa
    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        CategoryModel category = this.categoryService.findById(id);
        model.addAttribute("category", category);
        return "admin/category/form";
    }

    @PostMapping("/edit-category")
    public String update(@ModelAttribute("category") @Valid CategoryModel category,
                         BindingResult result, Model model) {
        if (this.categoryService.create(category)) {
            return "redirect:/admin/category/";

        }
        return "admin/category/form";

    }


    //Xóa
    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        categoryService.delete(id);
        return "redirect:/admin/category/";
    }


//    @GetMapping("/")
//    public String list() {
//        return "admin/category/list";
//    }
}
