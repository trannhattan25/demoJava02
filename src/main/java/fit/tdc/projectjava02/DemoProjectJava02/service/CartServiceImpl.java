package fit.tdc.projectjava02.DemoProjectJava02.service;

import fit.tdc.projectjava02.DemoProjectJava02.model.CartModel;
import fit.tdc.projectjava02.DemoProjectJava02.model.ProductModel;
import fit.tdc.projectjava02.DemoProjectJava02.model.UserModel;
import fit.tdc.projectjava02.DemoProjectJava02.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
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
    public boolean addProductToCart(UserModel user, ProductModel product) {
        CartModel cart = getOrCreateCart(user);
        int currentQuantity = cart.getQuantityForProduct(product.getId());
        if (currentQuantity >= product.getStockQty()) {
            return false;
        }
        cart.addProduct(product);
        cartRepository.save(cart);
        return true;
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
