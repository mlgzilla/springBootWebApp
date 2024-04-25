package task_tracker.service;

import org.springframework.stereotype.Service;
import task_tracker.domain.Project;
import task_tracker.domain.Task;
import task_tracker.dto.ProjectDto;
import task_tracker.repository.ProjectRepository;
import task_tracker.repository.TaskRepository;
import task_tracker.utils.Result;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

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

    public Result<String> addTask(UUID id, UUID taskId) {
        try {
            Optional<Project> projectRead = projectRepository.findById(id);
            if (projectRead.isEmpty())
                return Result.error("Project was not found", "404");
            Optional<Task> taskRead = taskRepository.findById(taskId);
            if (taskRead.isEmpty())
                return Result.error("Task was not found", "404");
            Project project = projectRead.get();
            Task task = taskRead.get();
            Set<Task> projectTasks = project.getTasks();
            projectTasks.add(task);
            project.setTasks(projectTasks);
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

    public ProjectService(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }
}
