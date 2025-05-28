package fit.tdc.projectjava02.DemoProjectJava02.service;

import fit.tdc.projectjava02.DemoProjectJava02.model.CartModel;
import fit.tdc.projectjava02.DemoProjectJava02.model.OrderModel;
import fit.tdc.projectjava02.DemoProjectJava02.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    boolean createOrder(UserModel user, CartModel cart,
                        String fullName,
                        String email,
                        String address,
                        String city,
                        String paymentMethod,
                        String cardNumber,
                        String expire);
    List<OrderModel> findOrdersByUser(UserModel user);
    OrderModel findByIdAndUser(Long orderId, UserModel user);
    List<OrderModel> findAllOrders();
    OrderModel findById(Long orderId);
    void updateOrderStatus(Long orderId, String status);
}
