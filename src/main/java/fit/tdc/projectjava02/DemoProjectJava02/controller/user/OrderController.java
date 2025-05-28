package fit.tdc.projectjava02.DemoProjectJava02.controller.user;

import fit.tdc.projectjava02.DemoProjectJava02.model.OrderModel;
import fit.tdc.projectjava02.DemoProjectJava02.model.UserModel;
import fit.tdc.projectjava02.DemoProjectJava02.service.OrderService;
import fit.tdc.projectjava02.DemoProjectJava02.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/index/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String viewOrders(Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        String username = authentication.getName();
        UserModel user = userService.findByUsername(username);
        List<OrderModel> orders = orderService.findOrdersByUser(user);

        model.addAttribute("orders", orders);
        return "user/orders";
    }

    @GetMapping("/detail")
    public String viewOrderDetail(@RequestParam("id") Long orderId, Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        String username = authentication.getName();
        UserModel user = userService.findByUsername(username);
        OrderModel order = orderService.findByIdAndUser(orderId, user);

        if (order == null) {
            model.addAttribute("error", "Đơn hàng không tồn tại hoặc bạn không có quyền xem.");
            return "user/orders";
        }

        model.addAttribute("order", order);
        return "user/order_detail";
    }
}