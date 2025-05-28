package fit.tdc.projectjava02.DemoProjectJava02.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Sản phẩm trong cart
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private ProductModel product;

    // Số lượng
    private int quantity;

    // Thuộc giỏ hàng nào
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private CartModel cart;
    // Custom constructor for ProductModel and quantity
    public CartItem(ProductModel product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}
