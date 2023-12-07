package egar.service;

import egar.domain.report.dto.ReportDtoRead;
import egar.domain.report.entity.Report;
import egar.domain.task.dto.TaskDtoRead;
import egar.domain.task.entity.Task;
import egar.enums.TaskStatus;
import egar.repository.ReportRepository;
import egar.repository.TaskRepository;
import egar.utils.Result;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ReportRepository reportRepository;

    public Result<TaskDtoRead> findById(Integer id){
        Task task = taskRepository.findById(id);
        if (task == null)
            return Result.error("Task was not found", "404");
        else
            return Result.ok(task.mapToDto());
    }

    public Result<List<TaskDtoRead>> findByEmployeeId(Integer id){
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

    public Result<List<TaskDtoRead>> findByStatus(TaskStatus status){
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

    public Result<List<ReportDtoRead>> findReportsByTaskId(Integer id){
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

    public Result<TaskDtoRead> create(Task task){
        try{
            task.setStatus(TaskStatus.NotStarted);
            Task savedTask = taskRepository.saveAndFlush(task);
            return Result.ok(savedTask.mapToDto());
        } catch (Exception e) {
            return Result.error("Failed to create task", "500");
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
