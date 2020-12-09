package com.endava.booksharing.model;

import com.endava.booksharing.model.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@Table(name = "t_role")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @ManyToMany(mappedBy = "userRoles")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<User> user;

    @Override
    public String getAuthority() {
        return this.roleType.toString();
    }
}
