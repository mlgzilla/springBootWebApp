package egar.repository;

import egar.domain.task.entity.Task;
import egar.enums.TaskStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends BaseRepository<Task, Integer> {

    @Query("select t from Task t where t.employee.id = :id")
    List<Task> findByEmployeeId(Integer id);

    @Query("select t from Task t where t.status = :taskStatus")
    List<Task> findByStatus(TaskStatus taskStatus);

}
