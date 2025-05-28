package fit.tdc.projectjava02.DemoProjectJava02.repository;

import fit.tdc.projectjava02.DemoProjectJava02.model.OrderModel;
import fit.tdc.projectjava02.DemoProjectJava02.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderModel, Long> {
    List<OrderModel> findByUser(UserModel user);
    Optional<OrderModel> findByIdAndUser(Long id, UserModel user);
}
