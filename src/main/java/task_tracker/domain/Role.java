package task_tracker.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import task_tracker.dto.RoleDto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
public class Role implements GrantedAuthority, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String name;

    @Transient
    @OneToMany(mappedBy = "roles")
    Set<User> users;

    public RoleDto mapToDto() {
        return new RoleDto(
                this.id,
                this.name
        );
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
