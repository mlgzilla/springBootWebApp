package task_tracker.domain;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import task_tracker.dto.UserDto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class User implements Serializable, UserDetails {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String name;

    private String surename;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role", referencedColumnName = "id", foreignKey = @ForeignKey(name = "users_fk"))
    private Role role;

    private String login;

    private String password;

    @Type(type = "jsonb")
    @Column(name = "projects", columnDefinition = "jsonb")
    Set<UUID> projects;

    @OneToMany(mappedBy = "user")
    private Set<Task> tasks;

    @OneToMany(mappedBy = "user")
    private Set<WorkTime> workTimes;

    @OneToOne(mappedBy = "user")
    private ContactInfo contactInfo;

    @OneToMany(mappedBy = "user")
    private Set<Attachment> attachments;

    @OneToMany(mappedBy = "user")
    private Set<Comment> comments;

    public UserDto mapToDto() {
        return new UserDto(
                this.id,
                this.name,
                this.surename,
                this.role,
                this.login,
                this.password,
                this.projects,
                this.contactInfo.mapToDto()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(getRole().getAuthority()));
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
