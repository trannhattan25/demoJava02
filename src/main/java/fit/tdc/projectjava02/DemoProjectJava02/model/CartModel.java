package fit.tdc.projectjava02.DemoProjectJava02.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mỗi cart chỉ thuộc về 1 user
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private UserModel user;

    // Danh sách sản phẩm trong cart
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    // Phương thức thêm sản phẩm vào giỏ
    public void addProduct(ProductModel product) {
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        CartItem newItem = new CartItem();
        newItem.setProduct(product);
        newItem.setQuantity(1);
        newItem.setCart(this);
        items.add(newItem);
    }

    public void updateProductQuantity(ProductModel product, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                if (quantity <= 0) {
                    items.remove(item);
                } else {
                    item.setQuantity(quantity);
                }
                return;
            }
        }
    }

    public void removeProduct(Long productId) {
        items.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    public int getQuantityForProduct(Long productId) {
        return items.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    public void clearItems() {
        items.clear();
    }


}
