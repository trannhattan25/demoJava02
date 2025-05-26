package fit.tdc.projectjava02.DemoProjectJava02.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping({"/"})
    public String dashboard() {
        return "redirect:/admin/product/";
    }

    @GetMapping("/layout")
    public String layout() {
        return "admin/layout";
    }


    @GetMapping("/manage")
    public String manage() {
        return "admin/manage";
    }

    @GetMapping("/add")
    public String add() {
        return "admin/add";
    }

    @GetMapping("/update")
    public String update() {
        return "admin/update";
    }


}
