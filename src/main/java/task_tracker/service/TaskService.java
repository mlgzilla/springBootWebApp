package task_tracker.service;

import org.springframework.stereotype.Service;
import task_tracker.domain.Task;
import task_tracker.dto.TaskDto;
import task_tracker.enums.Priority;
import task_tracker.enums.TaskStatus;
import task_tracker.repository.AttachmentRepository;
import task_tracker.repository.CommentRepository;
import task_tracker.repository.TaskRepository;
import task_tracker.utils.Result;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;
    private final AttachmentRepository attachmentRepository;

    public Result<TaskDto> findById(UUID id) {
        try {
            Optional<Task> task = taskRepository.findById(id);
            if (task.isEmpty())
                return Result.error("Task was not found", "404");
            else
                return Result.ok(task.get().mapToDto());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding task", "500");
        }
    }

    public Result<List<TaskDto>> findByUserId(UUID id) {
        try {
            List<Task> tasks = taskRepository.findByUserId(id);
            if (tasks.isEmpty())
                return Result.error("Tasks by employee were not found", "404");
            else
                return Result.ok(tasks.stream().map(Task::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding tasks by employee", "500");
        }
    }

    public Result<List<TaskDto>> findByStatus(TaskStatus status) {
        try {
            List<Task> tasks = taskRepository.findByStatus(status);
            if (tasks.isEmpty())
                return Result.error("Tasks by status were not found", "404");
            else
                return Result.ok(tasks.stream().map(Task::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding status by employee", "500");
        }
    }

    public Result<List<TaskDto>> findByPriority(Priority priority) {
        try {
            List<Task> tasks = taskRepository.findByPriority(priority);
            if (tasks.isEmpty())
                return Result.error("Tasks by priority were not found", "404");
            else
                return Result.ok(tasks.stream().map(Task::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding status by employee", "500");
        }
    }

    public Result<TaskDto> create(Task task) {
        try {
            task.setStatus(TaskStatus.NotStarted);
            Task savedTask = taskRepository.saveAndFlush(task);
            return Result.ok(savedTask.mapToDto());
        } catch (Exception e) {
            return Result.error("Failed to create task", "500");
        }
    }

    public Result<String> update(UUID id, TaskDto taskDto) {
        try {
            Optional<Task> taskRead = taskRepository.findById(id);
            if (taskRead.isEmpty())
                return Result.error("Task was not found", "404");
            Task task = taskRead.get();
            task.setName(taskDto.getName());
            task.setDescription(taskDto.getDescription());
            task.setDeadline(taskDto.getDeadline());
            taskRepository.saveAndFlush(task);
            return Result.ok("Update ok");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to update task", "500");
        }
    }

    public Result<String> updateStatus(UUID id, TaskStatus taskStatus) {
        try {
            Optional<Task> taskRead = taskRepository.findById(id);
            if (taskRead.isEmpty())
                return Result.error("Task was not found", "404");
            Task task = taskRead.get();
            task.setStatus(taskStatus);
            taskRepository.saveAndFlush(task);
            return Result.ok("Update ok");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to update task", "500");
        }
    }

    public Result<String> updatePriority(UUID id, Priority priority) {
        try {
            Optional<Task> taskRead = taskRepository.findById(id);
            if (taskRead.isEmpty())
                return Result.error("Task was not found", "404");
            Task task = taskRead.get();
            task.setPriority(priority);
            taskRepository.saveAndFlush(task);
            return Result.ok("Update ok");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to update task", "500");
        }
    }

    public Result<String> delete(UUID id) {
        try {
            taskRepository.deleteById(id);
            commentRepository.deleteAllByTaskId(id);
            attachmentRepository.deleteAllByTaskId(id);
            return Result.ok("Delete ok");
        } catch (Exception e) {
            return Result.error("Failed to delete task", "500");
        }
    }

    public TaskService(TaskRepository taskRepository, CommentRepository commentRepository, AttachmentRepository attachmentRepository) {
        this.taskRepository = taskRepository;
        this.commentRepository = commentRepository;
        this.attachmentRepository = attachmentRepository;
    }
}