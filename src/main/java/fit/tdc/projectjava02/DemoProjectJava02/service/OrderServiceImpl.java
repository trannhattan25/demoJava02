package fit.tdc.projectjava02.DemoProjectJava02.service;

import fit.tdc.projectjava02.DemoProjectJava02.model.*;
import fit.tdc.projectjava02.DemoProjectJava02.repository.OrderRepository;
import fit.tdc.projectjava02.DemoProjectJava02.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public boolean createOrder(UserModel user, CartModel cart,
                               String fullName,
                               String email,
                               String address,
                               String city,
                               String paymentMethod,
                               String cardNumber,
                               String expiry) {
        if (cart.getItems().isEmpty()) return false;

        // Kiểm tra tồn kho
        for (CartItem item : cart.getItems()) {
            if (item.getQuantity() > item.getProduct().getStockQty()) {
                return false;
            }
        }

        // Cập nhật tồn kho
        for (CartItem item : cart.getItems()) {
            ProductModel product = item.getProduct();
            product.setStockQty(product.getStockQty() - item.getQuantity());
            productRepository.save(product);
        }

        // Tạo đơn hàng
        OrderModel order = new OrderModel();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(address);
        order.setCity(city);
        order.setRecipientName(fullName);
        order.setRecipientEmail(email);
        order.setStatus("Pending");
        order.setPaymentMethod(paymentMethod);

        double totalAmount = 0;
        for (CartItem item : cart.getItems()) {
            double itemPrice = item.getProduct().getPrice();
            int quantity = item.getQuantity();

            totalAmount += itemPrice * quantity;

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(quantity);
            orderItem.setPrice(itemPrice);

            order.addOrderItem(orderItem);
        }

        order.setTotalAmount(totalAmount);

        // Lưu đơn hàng
        orderRepository.save(order);

        return true;
    }
}
