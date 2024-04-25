package task_tracker.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import task_tracker.dto.UserDto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String name;

    private String surename;

    @ManyToMany
    @JoinColumn(name = "roles", referencedColumnName = "id", foreignKey = @ForeignKey(name = "users_fk2"))
    private Set<Role> roles;

    private String login;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Project> projects;

    @OneToMany(mappedBy = "assignee")
    private Set<Task> tasks;

    @OneToMany(mappedBy = "user")
    private Set<WorkTime> workTimes;

    @OneToOne(mappedBy = "user")
    private ContactInfo contactInfos;

    @OneToMany(mappedBy = "uploader")
    private Set<Attachment> attachments;

    @OneToMany(mappedBy = "author")
    private Set<Comment> comments;

    public UserDto mapToDto() {
        return new UserDto(
                this.id,
                this.name,
                this.surename,
                this.roles,
                this.login,
                this.password,
                this.projects
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getUsername() {
        return getLogin();
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
