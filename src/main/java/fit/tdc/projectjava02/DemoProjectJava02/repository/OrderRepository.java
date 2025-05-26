package fit.tdc.projectjava02.DemoProjectJava02.repository;

import fit.tdc.projectjava02.DemoProjectJava02.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderModel, Long> {
}
