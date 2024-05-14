package task_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import task_tracker.domain.Task;
import task_tracker.enums.Priority;
import task_tracker.enums.TaskStatus;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Query("select t from Task t where t.user.id = :id")
    List<Task> findByUserId(UUID id);

    @Query("select t from Task t where t.status = :taskStatus")
    List<Task> findByStatus(TaskStatus taskStatus);

    @Query("select t from Task t where t.priority = :priority")
    List<Task> findByPriority(Priority priority);

    @Query("select t from Task t where ?1 is null or lower(t.name) like ?1")
    List<Task> findByName(String name);

    @Modifying
    @Transactional
    @Query("delete from Task where user.id = ?1")
    void deleteAllByUserId(UUID id);

}
