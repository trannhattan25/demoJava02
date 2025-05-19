package fit.tdc.projectjava02.DemoProjectJava02.config;

import fit.tdc.projectjava02.DemoProjectJava02.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private CustomUserDetailService customUserDetailService;


    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/static/**", "/fe/**").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/admin/", true)
                        .permitAll()
                )
                .logout(logout -> logout.permitAll().logoutSuccessUrl("login"))
                .logout(logout -> logout
                        .logoutUrl("/logout")  // URL để gọi logout, mặc định là /logout
                        .logoutSuccessUrl("/login?logout")  // chuyển về trang login có thông báo logout
                        .invalidateHttpSession(true)  // xóa session
                        .deleteCookies("JSESSIONID")  // xóa cookie session
                        .permitAll()
                );;

        return httpSecurity.build();
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer(){
        return ((web) -> web.debug(true).ignoring()
                .requestMatchers("/static/**","/fe/**","/assets/**"));
    }

}
