package task_tracker.service;

import org.springframework.stereotype.Service;
import task_tracker.domain.Project;
import task_tracker.domain.User;
import task_tracker.dto.ProjectDto;
import task_tracker.repository.ProjectRepository;
import task_tracker.repository.UserRepository;
import task_tracker.utils.Result;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public Result<ProjectDto> findById(UUID id) {
        try {
            Optional<Project> project = projectRepository.findById(id);
            if (project.isEmpty())
                return Result.error("Project by id were not found", "404");
            else
                return Result.ok(project.get().mapToDto());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding Projects by task id", "500");
        }
    }

    public Result<ProjectDto> create(Project project) {
        try {
            Project savedProject = projectRepository.saveAndFlush(project);
            return Result.ok(savedProject.mapToDto());
        } catch (Exception e) {
            return Result.error("Error creating Project", "500");
        }
    }

    public Result<String> update(UUID id, String name) {
        try {
            Optional<Project> projectRead = projectRepository.findById(id);
            if (projectRead.isEmpty())
                return Result.error("Project was not found", "404");
            Project project = projectRead.get();
            project.setName(name);
            projectRepository.saveAndFlush(project);
            return Result.ok("Update ok");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to update project", "500");
        }
    }

    public Result<String> addUser(UUID id, UUID userId) {
        try {
            Optional<Project> projectRead = projectRepository.findById(id);
            if (projectRead.isEmpty())
                return Result.error("Project was not found", "404");
            Optional<User> userRead = userRepository.findById(userId);
            if (userRead.isEmpty())
                return Result.error("User was not found", "404");
            Project project = projectRead.get();
            User user = userRead.get();
            Set<User> projectUsers = project.getUsers();
            projectUsers.add(user);
            project.setUsers(projectUsers);
            projectRepository.saveAndFlush(project);
            return Result.ok("Update ok");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to update project", "500");
        }
    }

    public Result<String> delete(UUID id) {
        try {
            projectRepository.deleteById(id);
            return Result.ok("Delete ok");
        } catch (Exception e) {
            return Result.error("Failed to delete project", "500");
        }
    }

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }
}
