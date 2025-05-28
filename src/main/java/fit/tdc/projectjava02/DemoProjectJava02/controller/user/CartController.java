package fit.tdc.projectjava02.DemoProjectJava02.controller.user;

import fit.tdc.projectjava02.DemoProjectJava02.model.CartItem;
import fit.tdc.projectjava02.DemoProjectJava02.model.CartModel;
import fit.tdc.projectjava02.DemoProjectJava02.model.ProductModel;
import fit.tdc.projectjava02.DemoProjectJava02.model.UserModel;
import fit.tdc.projectjava02.DemoProjectJava02.service.CartService;
import fit.tdc.projectjava02.DemoProjectJava02.service.OrderService;
import fit.tdc.projectjava02.DemoProjectJava02.service.ProductService;
import fit.tdc.projectjava02.DemoProjectJava02.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/index/cart")

public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public String viewCart(Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        String username = authentication.getName();
        UserModel user = userService.findByUsername(username);

        CartModel cart = cartService.getOrCreateCart(user);

        // Tính tổng tiền đơn giản không định dạng
        double totalPrice = 0;
        for (CartItem item : cart.getItems()) {
            totalPrice += item.getProduct().getPrice() * item.getQuantity();
        }

        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", totalPrice);

        return "user/cart";  // Thymeleaf template
    }


    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") Long productId,
                            @RequestParam(value = "quantity", defaultValue = "1") int quantity,
                            Authentication authentication, Model model) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        String username = authentication.getName();
        UserModel user = userService.findByUsername(username);
        if (user == null) {
            model.addAttribute("error", "Không tìm thấy người dùng.");
            return "redirect:/login";
        }

        ProductModel product = productService.findById(productId);
        if (product == null) {
            model.addAttribute("error", "Sản phẩm không tồn tại.");
            return "redirect:/index";
        }

        if (quantity <= 0 || quantity > product.getStockQty()) {
            model.addAttribute("error", "Số lượng không hợp lệ hoặc vượt quá tồn kho (" + product.getStockQty() + ").");
            return "redirect:/detail?id=" + productId;
        }

        boolean success = cartService.addProductToCart(user, product, quantity);
        if (!success) {
            model.addAttribute("error", "Không thể thêm sản phẩm vào giỏ hàng. Vui lòng thử lại.");
            return "redirect:/detail?id=" + productId;
        }

        model.addAttribute("success", "Đã thêm " + quantity + " " + product.getName() + " vào giỏ hàng.");
        return "redirect:/index/cart/";
    }

    @PostMapping("/update")
    public String updateQuantity(@RequestParam("productId") Long productId,
                                 @RequestParam("quantity") int quantity,
                                 Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        UserModel user = userService.findByUsername(authentication.getName());
        ProductModel product = productService.findById(productId);

        if (product == null || quantity > product.getStockQty()) {
            return "redirect:/index/cart/";
        }

        cartService.updateProductQuantity(user, product, quantity);
        return "redirect:/index/cart/";
    }

    @PostMapping("/remove")
    public String removeItem(@RequestParam("productId") Long productId,
                             Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        UserModel user = userService.findByUsername(authentication.getName());
        cartService.removeProductFromCart(user, productId);
        return "redirect:/index/cart/";
    }

    @GetMapping("/checkout")
    public String checkoutPage(Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
        String username = authentication.getName();
        UserModel user = userService.findByUsername(username);
        CartModel cart = cartService.getOrCreateCart(user);

        // Tính tổng tiền
        double totalPrice = 0;
        for (CartItem item : cart.getItems()) {
            totalPrice += item.getProduct().getPrice() * item.getQuantity();
        }

        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", totalPrice);

        return "user/payout";  // Tên file Thymeleaf
    }


    @PostMapping("/checkout")
    public String processCheckout(
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String address,
            @RequestParam String city,
            @RequestParam String paymentMethod,
            @RequestParam String cardNumber,
            @RequestParam String expiry,
            Authentication authentication,
            Model model) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        String username = authentication.getName();
        UserModel user = userService.findByUsername(username);

        CartModel cart = cartService.getOrCreateCart(user);
        if (cart.getItems().isEmpty()) {
            model.addAttribute("error", "Giỏ hàng của bạn đang trống!");
            return "user/payout"; // Trả về lại trang checkout
        }

        // Gọi service tạo đơn hàng
        orderService.createOrder(user, cart, fullName, email, address, city, paymentMethod, cardNumber, expiry);

        // Xóa giỏ hàng sau khi tạo đơn thành công
        cartService.clearCart(user);

        return "redirect:/index";  // Trang xác nhận đặt hàng
    }


}
