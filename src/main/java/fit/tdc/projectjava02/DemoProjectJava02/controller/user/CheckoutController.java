//package fit.tdc.projectjava02.DemoProjectJava02.controller.user;
//
//import fit.tdc.projectjava02.DemoProjectJava02.model.CartModel;
//import fit.tdc.projectjava02.DemoProjectJava02.model.UserModel;
//import fit.tdc.projectjava02.DemoProjectJava02.service.CartService;
//import fit.tdc.projectjava02.DemoProjectJava02.service.CheckoutRequest;
//import fit.tdc.projectjava02.DemoProjectJava02.service.OrderService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/checkout")
//public class CheckoutController {
//
//    private final OrderService orderService;
//    private final CartService cartService; // Giả sử bạn có CartService
//
//    public CheckoutController(OrderService orderService, CartService cartService) {
//        this.orderService = orderService;
//        this.cartService = cartService;
//    }
//
//    @PostMapping
//    public ResponseEntity<?> placeOrder(@RequestBody CheckoutRequest request, @AuthenticationPrincipal UserModel user) {
//        CartModel cart = cartService.getCartByUser(user);
//
//        boolean success = orderService.createOrder(user, cart, request);
//
//        if (success) {
//            cartService.clearCart(cart);
//            return ResponseEntity.ok("Đặt hàng thành công");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Đặt hàng thất bại");
//        }
//    }
//}
