package fit.tdc.projectjava02.DemoProjectJava02.service;

import fit.tdc.projectjava02.DemoProjectJava02.model.UserModel;

import java.util.List;

public interface UserService {
    UserModel findByUsername(String userName);
    void save(UserModel user, String roleName);


}
