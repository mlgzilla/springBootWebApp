package task_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import task_tracker.domain.Comment;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

    @Query("select c from Comment c where c.task.id = :id")
    List<Comment> findByTaskId(UUID id);

    @Query("select c from Comment c where c.task.id = ?1")
    void deleteAllByTaskId(UUID id);

}
