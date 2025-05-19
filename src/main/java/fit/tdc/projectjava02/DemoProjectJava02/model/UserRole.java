package fit.tdc.projectjava02.DemoProjectJava02.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users_role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "id")
    private Role role;

}
