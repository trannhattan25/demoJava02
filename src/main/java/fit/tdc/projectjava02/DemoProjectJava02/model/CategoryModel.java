package fit.tdc.projectjava02.DemoProjectJava02.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @NotBlank(message = "Tên danh mục không được để trống")
    @Size(max = 100, message = "Tên danh mục tối đa 100 ký tự")
    private String name;

    @Size(max = 255, message = "Mô tả tối đa 255 ký tự")
    private String description;

    @OneToMany(mappedBy = "category")
    private Set<ProductModel> products;


}
