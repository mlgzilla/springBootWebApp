package task_tracker.dto;

import lombok.Data;
import task_tracker.domain.Project;
import task_tracker.domain.Role;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String name;
    private String surename;
    private Set<Role> roles;
    private String login;
    private String password;
    private Set<Project> projects;

    public UserDto(UUID id, String name, String surename, Set<Role> roles, String login, String password, Set<Project> projects) {
        this.id = id;
        this.name = name;
        this.surename = surename;
        this.roles = roles;
        this.login = login;
        this.password = password;
        this.projects = projects;
    }
}
