package fit.tdc.projectjava02.DemoProjectJava02.controller.admin;

import fit.tdc.projectjava02.DemoProjectJava02.model.UserModel;
import fit.tdc.projectjava02.DemoProjectJava02.service.CategoryService;
import fit.tdc.projectjava02.DemoProjectJava02.service.ProductService;
import fit.tdc.projectjava02.DemoProjectJava02.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    //form
    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserModel user) {
        userService.save(user, "USER"); // Assign default role
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register"; // Thymeleaf template name (register.html)
    }


}
