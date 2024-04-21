package task_tracker.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import task_tracker.domain.Task;
import task_tracker.enums.TaskStatus;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends BaseRepository<Task, UUID> {

    @Query("select t from Task t where t.user.id = :id")
    List<Task> findByEmployeeId(UUID id);

    @Query("select t from Task t where t.status = :taskStatus")
    List<Task> findByStatus(TaskStatus taskStatus);

    @Query("select t from Task t where t.user.id = ?1")
    void deleteAllByEmployeeId(UUID id);

}
