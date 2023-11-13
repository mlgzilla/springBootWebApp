package egar.service;

import egar.domain.report.dto.ReportDtoRead;
import egar.domain.report.entity.Report;
import egar.domain.task.dto.TaskDtoRead;
import egar.domain.task.entity.Task;
import egar.repository.ReportRepository;
import egar.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {
    private TaskRepository taskRepository;
    private ReportRepository reportRepository;

    public Optional<TaskDtoRead> findById(Integer id){
        return taskRepository.findById(id).map
                (task -> new TaskDtoRead(
                        task.getId(),
                        task.getName(),
                        task.getDescription(),
                        task.getDueTime(),
                        task.getEmployee().getId(),
                        task.getStatus()
                ));
    }

    public Optional<TaskDtoRead> create(Task task){
        try{
            Task savedTask = taskRepository.saveAndFlush(task);
            return Optional.of(new TaskDtoRead(
                    savedTask.getId(),
                    savedTask.getName(),
                    savedTask.getDescription(),
                    savedTask.getDueTime(),
                    savedTask.getEmployee().getId(),
                    savedTask.getStatus()
            ));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
