package fit.tdc.projectjava02.DemoProjectJava02.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
