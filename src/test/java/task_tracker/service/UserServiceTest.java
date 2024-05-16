package task_tracker.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import task_tracker.domain.ContactInfo;
import task_tracker.domain.Role;
import task_tracker.domain.User;
import task_tracker.dto.UserDto;
import task_tracker.repository.ContactInfoRepository;
import task_tracker.repository.RoleRepository;
import task_tracker.repository.UserRepository;
import task_tracker.utils.Result;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ContactInfoRepository contactInfoRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testFindById_UserFound() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setContactInfo(new ContactInfo());
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Result<UserDto> result = userService.findById(userId);

        Assertions.assertEquals(userId, result.getObject().getId());
    }

    @Test
    public void testFindById_UserNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Result<UserDto> result = userService.findById(userId);

        Assertions.assertEquals("User was not found", result.getMessage());
        Assertions.assertEquals("404", result.getCode());
    }

    @Test
    public void testFindByPrincipal_UserFound() {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("johndoe");

        User user = new User();
        user.setLogin("johndoe");
        user.setContactInfo(new ContactInfo());
        when(userRepository.findByLogin("johndoe")).thenReturn(Optional.of(user));

        Result<UserDto> result = userService.findByPrincipal(principal);

        Assertions.assertEquals("johndoe", result.getObject().getLogin());
    }

    @Test
    public void testFindByPrincipal_UserNotFound() {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("janedoe");
        when(userRepository.findByLogin("janedoe")).thenReturn(Optional.empty());

        Result<UserDto> result = userService.findByPrincipal(principal);

        Assertions.assertEquals("User was not found", result.getMessage());
        Assertions.assertEquals("404", result.getCode());
    }

    @Test
    public void testUserExists_UserExists() {
        when(userRepository.findByLogin("johndoe")).thenReturn(Optional.of(new User()));

        boolean exists = userService.userExists("johndoe");

        Assertions.assertTrue(exists);
    }

    @Test
    public void testUserExists_UserDoesNotExist() {
        when(userRepository.findByLogin("janedoe")).thenReturn(Optional.empty());

        boolean exists = userService.userExists("janedoe");

        Assertions.assertFalse(exists);
    }

    @Test
    public void testFindByName_Success() {
        String name = "John";
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setName("John");
        user.setSurename("Doe");
        user.setLogin("login");
        user.setPassword("1234");
        user.setContactInfo(new ContactInfo());
        users.add(user);
        when(userRepository.findByName(name + '%')).thenReturn(users);

        Result<List<UserDto>> result = userService.findByName(name);

        Assertions.assertEquals(1, result.getObject().size());
        Assertions.assertEquals("John", result.getObject().get(0).getName());
    }

    @Test
    public void testFindBySurename_Success() {
        String surename = "Doe";
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setName("John");
        user.setSurename("Doe");
        user.setLogin("login");
        user.setPassword("1234");
        user.setContactInfo(new ContactInfo());
        users.add(user);
        when(userRepository.findBySurename(surename + '%')).thenReturn(users);

        Result<List<UserDto>> result = userService.findBySurename(surename);

        Assertions.assertEquals(1, result.getObject().size());
        Assertions.assertEquals("Doe", result.getObject().get(0).getSurename());
    }

    @Test
    public void testCreate_Success() {
        User user = new User();
        user.setName("John");
        user.setSurename("Doe");
        user.setLogin("login");
        user.setPassword("1234");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(new Role()));
        when(userRepository.saveAndFlush(any(User.class))).thenReturn(user);
        when(contactInfoRepository.saveAndFlush(any(ContactInfo.class))).thenReturn(new ContactInfo());

        Result<User> result = userService.create(user);

        Assertions.assertEquals(user, result.getObject());
    }

    @Test
    public void testCreate_Failure() {
        User user = new User();
        user.setName("John");
        user.setSurename("Doe");
        user.setLogin("login");
        user.setPassword("1234");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(new Role()));
        when(userRepository.saveAndFlush(any(User.class))).thenThrow(new RuntimeException("Failed to save user"));

        Result<User> result = userService.create(user);

        Assertions.assertEquals("Error creating User", result.getMessage());
        Assertions.assertEquals("500", result.getCode());
    }

    @Test
    public void testUpdate_Success() {
        UUID userId = UUID.randomUUID();
        UserDto userDto = new UserDto();
        userDto.setName("John");
        userDto.setSurename("Doe");
        userDto.setLogin("login");
        userDto.setPassword("1234");
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setName("OldName");
        existingUser.setPassword("oldpassword");
        existingUser.setSurename("OldSurename");
        existingUser.setLogin("oldlogin");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.saveAndFlush(any(User.class))).thenReturn(existingUser);

        Result<String> result = userService.update(userId, userDto);

        Assertions.assertEquals("Update ok", result.getObject());
    }

    @Test
    public void testUpdate_UserNotFound() {
        UUID userId = UUID.randomUUID();
        UserDto userDto = new UserDto();
        userDto.setName("John");
        userDto.setSurename("Doe");
        userDto.setLogin("login");
        userDto.setPassword("1234");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Result<String> result = userService.update(userId, userDto);

        Assertions.assertEquals("User was not found", result.getMessage());
        Assertions.assertEquals("404", result.getCode());
    }

    @Test
    public void testDelete_Failure() {
        UUID userId = UUID.randomUUID();

        Mockito.doThrow(new RuntimeException("Failed to delete user")).when(userRepository).deleteById(userId);

        Result<String> result = userService.delete(userId);

        Assertions.assertEquals("Failed to delete user", result.getMessage());
        Assertions.assertEquals("500", result.getCode());
    }
}
