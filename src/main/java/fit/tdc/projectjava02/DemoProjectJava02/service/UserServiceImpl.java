package fit.tdc.projectjava02.DemoProjectJava02.service;

import fit.tdc.projectjava02.DemoProjectJava02.config.PasswordEncoderConfig;
import fit.tdc.projectjava02.DemoProjectJava02.model.Role;
import fit.tdc.projectjava02.DemoProjectJava02.model.UserModel;
import fit.tdc.projectjava02.DemoProjectJava02.model.UserRole;
import fit.tdc.projectjava02.DemoProjectJava02.repository.RoleRepository;
import fit.tdc.projectjava02.DemoProjectJava02.repository.UserRepository;
import fit.tdc.projectjava02.DemoProjectJava02.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public UserModel findByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public void save(UserModel user, String roleName) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);

        userRepository.save(user); // Save user first to generate ID

        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);

        userRoleRepository.save(userRole);

    }
}
