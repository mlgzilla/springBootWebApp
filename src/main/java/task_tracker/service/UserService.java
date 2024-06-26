package task_tracker.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import task_tracker.domain.ContactInfo;
import task_tracker.domain.Role;
import task_tracker.domain.User;
import task_tracker.dto.UserDto;
import task_tracker.repository.*;
import task_tracker.utils.Result;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AttachmentRepository attachmentRepository;
    private final TaskRepository taskRepository;
    private final WorkTimeRepository workTimeRepository;
    private final ContactInfoRepository contactInfoRepository;
    private final RoleRepository roleRepository;
    private final CommentRepository commentRepository;

    public UserService(UserRepository userRepository, AttachmentRepository attachmentRepository, TaskRepository taskRepository, WorkTimeRepository workTimeRepository, ContactInfoRepository contactInfoRepository, RoleRepository roleRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.attachmentRepository = attachmentRepository;
        this.taskRepository = taskRepository;
        this.workTimeRepository = workTimeRepository;
        this.contactInfoRepository = contactInfoRepository;
        this.roleRepository = roleRepository;
        this.commentRepository = commentRepository;
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

    public Result<UserDto> findByPrincipal(Principal principal) {
        try {
            Optional<User> user = userRepository.findByLogin(principal.getName());
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
            return Result.ok(users.stream().map(User::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding Users by first name", "500");
        }
    }

    public Result<List<UserDto>> findBySurename(String surename) {
        try {
            List<User> users = userRepository.findBySurename(surename + '%');
            return Result.ok(users.stream().map(User::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding Users by middle name", "500");
        }
    }

    @Transactional
    public Result<User> create(User user) {
        try {
            Optional<Role> result = roleRepository.findByName("ROLE_USER");
            Role role = result.get();
            user.setRole(role);
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            User savedUser = userRepository.saveAndFlush(user);
            ContactInfo contactInfo = new ContactInfo();
            contactInfo.setUserr(savedUser);
            ContactInfo savedContactInfo = contactInfoRepository.saveAndFlush(contactInfo);
            return Result.ok(savedUser);
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
            if (!user.getPassword().equals(""))
                user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
            user.setSurename(userDto.getSurename());
            user.setLogin(userDto.getLogin());
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
            commentRepository.deleteAllByUserId(id);
            taskRepository.deleteAllByUserId(id);
            workTimeRepository.deleteAllByUserId(id);
            contactInfoRepository.deleteAllByUserId(id);
            userRepository.deleteById(id);
            return Result.ok("Delete ok");
        } catch (Exception e) {
            return Result.error("Failed to delete user", "500");
        }
    }
}
