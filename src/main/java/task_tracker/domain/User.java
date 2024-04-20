package task_tracker.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import task_tracker.dto.UserDto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String name;

    private String surename;

    @OneToOne
    @JoinColumn(name = "role", referencedColumnName = "id")
    private Role role;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "assignee")
    private Set<Task> tasks;

    @OneToMany(mappedBy = "user")
    private Set<WorkTime> workTimes;

    @OneToMany(mappedBy = "user_id")
    private Set<ContactInfo> contactInfos;

    @OneToMany(mappedBy = "uploader")
    private Set<Attachment> attachments;

    @OneToMany(mappedBy = "author")
    private Set<Comment> comments;

    public UserDto mapToDto() {
        return new UserDto(
                this.id,
                this.name,
                this.surename,
                this.role,
                this.login,
                this.password
        );
    }
}
