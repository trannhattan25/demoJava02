package fit.tdc.projectjava02.DemoProjectJava02.service;

import fit.tdc.projectjava02.DemoProjectJava02.model.CartModel;
import fit.tdc.projectjava02.DemoProjectJava02.model.ProductModel;
import fit.tdc.projectjava02.DemoProjectJava02.model.UserModel;

public interface CartService {

    CartModel getOrCreateCart(UserModel user);
    CartModel getCartByUser(UserModel user);
    void clearCart(UserModel user);

    boolean addProductToCart(UserModel user, ProductModel product);

    void updateProductQuantity(UserModel user, ProductModel product, int quantity);

    void removeProductFromCart(UserModel user, Long productId);

    double calculateTotalPrice(UserModel user);
}
