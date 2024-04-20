package task_tracker.service;

import task_tracker.dto.ReportDto;
import task_tracker.domain.Report;
import task_tracker.dto.TaskDto;
import task_tracker.domain.Task;
import task_tracker.enums.TaskStatus;
import task_tracker.repository.ReportRepository;
import task_tracker.repository.TaskRepository;
import task_tracker.utils.Result;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ReportRepository reportRepository;

    public Result<TaskDto> findById(Integer id) {
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

    public Result<List<TaskDto>> findByEmployeeId(Integer id) {
        try {
            List<Task> tasks = taskRepository.findByEmployeeId(id);
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

    public Result<List<ReportDto>> findReportsByTaskId(Integer id) {
        try {
            List<Report> reports = reportRepository.findByTaskId(id);
            if (reports.isEmpty())
                return Result.error("Reports by Task were not found", "404");
            else
                return Result.ok(reports.stream().map(Report::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding Reports by Task", "500");
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

    public Result<String> update(Integer id, TaskDto taskDto) {
        try {
            Optional<Task> taskRead = taskRepository.findById(id);
            if (taskRead.isEmpty())
                return Result.error("Task was not found", "404");
            Task task = taskRead.get();
            task.setName(taskDto.getName());
            task.setDescription(taskDto.getDescription());
            task.setDueTime(taskDto.getDueTime());
            taskRepository.saveAndFlush(task);
            return Result.ok("Update ok");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to update task", "500");
        }
    }

    public Result<String> updateStatus(Integer id, TaskStatus taskStatus) {
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

    public Result<String> delete(Integer id) {
        try {
            reportRepository.deleteAllByTaskId(id);
            taskRepository.deleteById(id);
            return Result.ok("Delete ok");
        } catch (Exception e) {
            return Result.error("Failed to delete task", "500");
        }
    }

    public TaskService(TaskRepository taskRepository, ReportRepository reportRepository) {
        this.taskRepository = taskRepository;
        this.reportRepository = reportRepository;
    }
}
