package task_tracker.service;

import org.springframework.stereotype.Service;
import task_tracker.domain.Role;
import task_tracker.dto.RoleDto;
import task_tracker.repository.RoleRepository;
import task_tracker.utils.Result;

import java.util.Optional;
import java.util.UUID;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public Result<RoleDto> findById(UUID id) {
        try {
            Optional<Role> role = roleRepository.findById(id);
            if (role.isEmpty())
                return Result.error("Role by id were not found", "404");
            else
                return Result.ok(role.get().mapToDto());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding Roles by task id", "500");
        }
    }

    public Result<RoleDto> create(Role role) {
        try {
            Role savedRole = roleRepository.saveAndFlush(role);
            return Result.ok(savedRole.mapToDto());
        } catch (Exception e) {
            return Result.error("Error creating Role", "500");
        }
    }

    public Result<String> update(UUID id, String name) {
        try {
            Optional<Role> roleRead = roleRepository.findById(id);
            if (roleRead.isEmpty())
                return Result.error("Role was not found", "404");
            Role role = roleRead.get();
            role.setName(name);
            roleRepository.saveAndFlush(role);
            return Result.ok("Update ok");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to update role", "500");
        }
    }

    public Result<String> delete(UUID id) {
        try {
            roleRepository.deleteById(id);
            return Result.ok("Delete ok");
        } catch (Exception e) {
            return Result.error("Failed to delete role", "500");
        }
    }

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
}
