package fit.tdc.projectjava02.DemoProjectJava02.service;

import fit.tdc.projectjava02.DemoProjectJava02.model.CustomUserDetails;
import fit.tdc.projectjava02.DemoProjectJava02.model.UserModel;
import fit.tdc.projectjava02.DemoProjectJava02.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Sai");
        }
        Collection<GrantedAuthority> authoritiySet = new HashSet<>();
        Set<UserRole> roles = user.getUserRoles();

        for (UserRole userRole : roles) {
            authoritiySet.add(new SimpleGrantedAuthority(userRole.getRole().getName()));
        }
        return new CustomUserDetails(user, authoritiySet);
    }

}
