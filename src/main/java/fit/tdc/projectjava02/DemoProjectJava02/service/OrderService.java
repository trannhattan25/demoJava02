package fit.tdc.projectjava02.DemoProjectJava02.service;

import fit.tdc.projectjava02.DemoProjectJava02.model.CartModel;
import fit.tdc.projectjava02.DemoProjectJava02.model.UserModel;

public interface OrderService {
    boolean createOrder(UserModel user, CartModel cart,
                        String fullName,
                        String email,
                        String address,
                        String city,
                        String paymentMethod,
                        String cardNumber,
                        String expire);
}
