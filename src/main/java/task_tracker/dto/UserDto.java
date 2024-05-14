package task_tracker.dto;

import lombok.Data;
import task_tracker.domain.Role;

import java.util.Set;
import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String name;
    private String surename;
    private Role role;
    private String login;
    private String password;
    private Set<UUID> projects;
    private ContactInfoDto contactInfoDto;

    public UserDto(UUID id, String name, String surename, Role role, String login, String password, Set<UUID> projects, ContactInfoDto contactInfoDto) {
        this.id = id;
        this.name = name;
        this.surename = surename;
        this.role = role;
        this.login = login;
        this.password = password;
        this.projects = projects;
        this.contactInfoDto = contactInfoDto;
    }
}
