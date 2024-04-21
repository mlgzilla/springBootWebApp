package task_tracker.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import task_tracker.domain.Attachment;

import java.util.List;
import java.util.UUID;

@Repository
public interface AttachmentRepository extends BaseRepository<Attachment, UUID> {

    @Query("select a from Attachment a where a.user.id = :id")
    List<Attachment> findByUserId(UUID id);

    @Query("select a from Attachment a where a.task.id = :id")
    List<Attachment> findByTaskId(UUID id);

//    @Query("select a from Attachment a where ?1 is null or lower(a.name) like ?1")
//    List<Attachment> findByName(String name);
//
//    @Query("select a from Attachment a where a.dateUploaded < cast(?1 as timestamp)")
//    List<Attachment> findByCreationDateBefore(LocalDateTime date);
//
//    @Query("select a from Attachment a where a.dateUploaded >= cast(?1 as timestamp)")
//    List<Attachment> findByCreationDateAfter(LocalDateTime date);

    @Query("select a from Attachment a where a.user.id = ?1")
    void deleteAllByUserId(UUID id);

    @Query("select a from Attachment a where a.task.id = ?1")
    void deleteAllByTaskId(UUID id);

}
