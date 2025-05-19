package fit.tdc.projectjava02.DemoProjectJava02.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products")
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;

    @DecimalMin(value = "0", message = "Giá phải lớn hơn 0")
    private double price;

    @Min(value = 0, message = "Số lượng phải >= 0")
    private int stockQty;

    private String description;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId")
    private CategoryModel category;

}
