package fit.tdc.projectjava02.DemoProjectJava02.repository;

import fit.tdc.projectjava02.DemoProjectJava02.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel,Long> {
    UserModel findByUsername(String userName);
}
