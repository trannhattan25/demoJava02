package fit.tdc.projectjava02.DemoProjectJava02.controller.admin;

import fit.tdc.projectjava02.DemoProjectJava02.model.OrderModel;
import fit.tdc.projectjava02.DemoProjectJava02.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
@PreAuthorize("hasRole('ADMIN')")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public String viewOrders(Model model) {
        List<OrderModel> orders = orderService.findAllOrders();
        model.addAttribute("orders", orders);
        return "admin/order/list";
    }

    @GetMapping("/detail")
    public String viewOrderDetail(@RequestParam("id") Long orderId, Model model) {
        OrderModel order = orderService.findById(orderId);
        if (order == null) {
            model.addAttribute("error", "Đơn hàng không tồn tại.");
            return "admin/order/list";
        }
        model.addAttribute("order", order);
        return "admin/order/order-detail";
    }

    @PostMapping("/update-status")
    public String updateOrderStatus(@RequestParam("orderId") Long orderId,
                                    @RequestParam("status") String status,
                                    Model model) {
        OrderModel order = orderService.findById(orderId);
        if (order == null) {
            model.addAttribute("error", "Đơn hàng không tồn tại.");
            return "redirect:/admin/orders";
        }

        try {
            orderService.updateOrderStatus(orderId, status);
            model.addAttribute("success", "Cập nhật trạng thái đơn hàng thành công.");
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi cập nhật trạng thái đơn hàng: " + e.getMessage());
        }
        return "redirect:/admin/orders/detail?id=" + orderId;
    }
}