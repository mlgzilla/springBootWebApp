package task_tracker.service;

import org.springframework.stereotype.Service;
import task_tracker.domain.Project;
import task_tracker.domain.Role;
import task_tracker.domain.Task;
import task_tracker.domain.User;
import task_tracker.dto.UserDto;
import task_tracker.repository.*;
import task_tracker.utils.Result;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AttachmentRepository attachmentRepository;
    private final TaskRepository taskRepository;
    private final WorkTimeRepository workTimeRepository;
    private final ContactInfoRepository contactInfoRepository;
    private final ProjectRepository projectRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, AttachmentRepository attachmentRepository, TaskRepository taskRepository, WorkTimeRepository workTimeRepository, ContactInfoRepository contactInfoRepository, ProjectRepository projectRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.attachmentRepository = attachmentRepository;
        this.taskRepository = taskRepository;
        this.workTimeRepository = workTimeRepository;
        this.contactInfoRepository = contactInfoRepository;
        this.projectRepository = projectRepository;
        this.roleRepository = roleRepository;
    }

    public Result<UserDto> findById(UUID id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isEmpty())
                return Result.error("User was not found", "404");
            else
                return Result.ok(user.get().mapToDto());
        } catch (Exception e) {
            return Result.error("Error finding user", "500");
        }
    }

    public Boolean userExists(String login) {
        return userRepository.findByLogin(login).isPresent();
    }

    public Result<List<UserDto>> findByName(String name) {
        try {
            List<User> users = userRepository.findByName(name + '%');
            if (users.isEmpty())
                return Result.error("Users by first name were not found", "404");
            else
                return Result.ok(users.stream().map(User::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding Users by first name", "500");
        }
    }

    public Result<List<UserDto>> findBySurename(String surename) {
        try {
            List<User> users = userRepository.findBySurename(surename + '%');
            if (users.isEmpty())
                return Result.error("Users by middle name were not found", "404");
            else
                return Result.ok(users.stream().map(User::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding Users by middle name", "500");
        }
    }

    public Result<UserDto> create(User user) {
        try {
            User savedUser = userRepository.saveAndFlush(user);
            return Result.ok(savedUser.mapToDto());
        } catch (Exception e) {
            return Result.error("Error creating User", "500");
        }
    }

    public Result<String> update(UUID id, UserDto userDto) {
        try {
            Optional<User> userRead = userRepository.findById(id);
            if (userRead.isEmpty())
                return Result.error("User was not found", "404");
            User user = userRead.get();
            user.setName(userDto.getName());
            user.setSurename(userDto.getSurename());
            userRepository.saveAndFlush(user);
            return Result.ok("Update ok");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to update user", "500");
        }
    }

//    public Result<String> addRole(UUID id, UUID roleId) {
//        try {
//            Optional<User> userRead = userRepository.findById(id);
//            if (userRead.isEmpty())
//                return Result.error("User was not found", "404");
//            Optional<Role> roleRead = roleRepository.findById(roleId);
//            if (roleRead.isEmpty())
//                return Result.error("Role was not found", "404");
//            User user = userRead.get();
//            Role role = roleRead.get();
//            Role userRoles = user.getRole();
//            userRoles.add(role);
//            user.setRoles(userRoles);
//            userRepository.saveAndFlush(user);
//            return Result.ok("Update ok");
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return Result.error("Failed to update user", "500");
//        }
//    }

    public Result<String> updatePassword(UUID id, String newPassword) {
        try {
            Optional<User> userRead = userRepository.findById(id);
            if (userRead.isEmpty())
                return Result.error("User was not found", "404");
            User user = userRead.get();
            user.setPassword(newPassword);
            userRepository.saveAndFlush(user);
            return Result.ok("Update ok");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to update user", "500");
        }
    }

    public Result<String> delete(UUID id) {
        try {
            attachmentRepository.deleteAllByUserId(id);
            taskRepository.deleteAllByUserId(id);
            workTimeRepository.deleteAllByUserId(id);
            contactInfoRepository.deleteByUserId(id);
            userRepository.deleteById(id);
            return Result.ok("Delete ok");
        } catch (Exception e) {
            return Result.error("Failed to delete user", "500");
        }
    }
}
