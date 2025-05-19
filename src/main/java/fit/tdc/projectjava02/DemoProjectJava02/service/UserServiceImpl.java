package fit.tdc.projectjava02.DemoProjectJava02.service;

import fit.tdc.projectjava02.DemoProjectJava02.model.UserModel;
import fit.tdc.projectjava02.DemoProjectJava02.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserModel findByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }
}
