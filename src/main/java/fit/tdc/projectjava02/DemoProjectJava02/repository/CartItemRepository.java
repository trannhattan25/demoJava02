package fit.tdc.projectjava02.DemoProjectJava02.repository;

import fit.tdc.projectjava02.DemoProjectJava02.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
