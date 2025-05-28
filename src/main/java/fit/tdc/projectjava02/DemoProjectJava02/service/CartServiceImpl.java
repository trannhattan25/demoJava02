package fit.tdc.projectjava02.DemoProjectJava02.service;

import fit.tdc.projectjava02.DemoProjectJava02.model.CartItem;
import fit.tdc.projectjava02.DemoProjectJava02.model.CartModel;
import fit.tdc.projectjava02.DemoProjectJava02.model.ProductModel;
import fit.tdc.projectjava02.DemoProjectJava02.model.UserModel;
import fit.tdc.projectjava02.DemoProjectJava02.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    private final CartRepository cartRepository;

    @Override
    public CartModel getCartByUser(UserModel user) {
        return cartRepository.findByUser(user).orElse(null);
    }

    @Override
    public void clearCart(UserModel user) {
        CartModel cart = getOrCreateCart(user);
        cart.clearItems();
        cartRepository.save(cart);
    }

    @Override
    public CartModel getOrCreateCart(UserModel user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    CartModel cart = new CartModel();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

    @Override
    public boolean addProductToCart(UserModel user, ProductModel product, int quantity) {
        try {
            logger.info("Adding product {} with quantity {} to cart for user {}", product.getId(), quantity, user.getId());

            CartModel cart = getOrCreateCart(user);

            // Kiểm tra sản phẩm đã có trong giỏ hàng
            Optional<CartItem> existingItemOpt = cart.getItems().stream()
                    .filter(item -> item.getProduct().getId().equals(product.getId()))
                    .findFirst();

            if (existingItemOpt.isPresent()) {
                CartItem existingItem = existingItemOpt.get();
                int newQuantity = existingItem.getQuantity() + quantity;
                if (newQuantity > product.getStockQty()) {
                    logger.warn("Quantity {} exceeds stock {} for product {}", newQuantity, product.getStockQty(), product.getId());
                    return false;
                }
                existingItem.setQuantity(newQuantity);
                cartRepository.save(cart); // Lưu thay đổi
                logger.info("Updated quantity for product {} to {}", product.getId(), newQuantity);
                return true;
            }

            // Kiểm tra số lượng trước khi thêm mới
            if (quantity > product.getStockQty()) {
                logger.warn("Quantity {} exceeds stock {} for product {}", quantity, product.getStockQty(), product.getId());
                return false;
            }

            // Thêm sản phẩm mới
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setCart(cart); // Thiết lập trường cart
            cart.getItems().add(newItem);
            cartRepository.save(cart); // Lưu giỏ hàng
            logger.info("Added new item for product {} with quantity {}", product.getId(), quantity);
            return true;
        } catch (Exception e) {
            logger.error("Error adding product {} to cart for user {}: {}", product.getId(), user.getId(), e.getMessage());
            return false;
        }
    }

    @Override
    public void updateProductQuantity(UserModel user, ProductModel product, int quantity) {
        CartModel cart = getOrCreateCart(user);
        cart.updateProductQuantity(product, quantity);
        cartRepository.save(cart);
    }

    @Override
    public void removeProductFromCart(UserModel user, Long productId) {
        CartModel cart = getOrCreateCart(user);
        cart.removeProduct(productId);
        cartRepository.save(cart);
    }

    @Override
    public double calculateTotalPrice(UserModel user) {
        CartModel cart = getOrCreateCart(user);
        return cart.getItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
                .sum();
    }
}