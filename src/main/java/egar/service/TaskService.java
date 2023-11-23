package egar.service;

import egar.domain.report.dto.ReportDtoRead;
import egar.domain.report.entity.Report;
import egar.domain.task.dto.TaskDtoRead;
import egar.domain.task.entity.Task;
import egar.enums.TaskStatus;
import egar.repository.ReportRepository;
import egar.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ReportRepository reportRepository;

    public Optional<TaskDtoRead> findById(Integer id){
        return taskRepository.findById(id).map(Task::mapToDto);
    }

    public Optional<TaskDtoRead> create(Task task){
        try{
            task.setStatus(TaskStatus.NotStarted);
            Task savedTask = taskRepository.saveAndFlush(task);
            return Optional.of(savedTask.mapToDto());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<TaskDtoRead> findByEmployeeId(Integer id){
        return taskRepository.findByEmployeeId(id).stream().map(Task::mapToDto).collect(Collectors.toList());
    }

    public List<TaskDtoRead> findByStatus(TaskStatus status){
        return taskRepository.findByStatus(status).stream().map(Task::mapToDto).collect(Collectors.toList());
    }

    public List<ReportDtoRead> findReportsByTaskId(Integer id){
        return reportRepository.findByTaskId(id).stream().map(Report::mapToDto).collect(Collectors.toList());
    }

    public TaskService(TaskRepository taskRepository, ReportRepository reportRepository) {
        this.taskRepository = taskRepository;
        this.reportRepository = reportRepository;
    }
}
