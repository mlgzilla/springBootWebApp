package task_tracker.repository;

import task_tracker.domain.Attachment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DocumentRepository extends BaseRepository<Attachment, Integer> {

    @Query("select d from Attachment d where d.employee.id = :id")
    List<Attachment> findByEmployeeId(Integer id);

    @Query("select d from Attachment d where ?1 is null or lower(d.name) like ?1")
    List<Attachment> findByName(String name);

    @Query("select d from Attachment d where d.creationDate < cast(?1 as timestamp)")
    List<Attachment> findByCreationDateBefore(LocalDateTime date);

    @Query("select d from Attachment d where d.creationDate >= cast(?1 as timestamp)")
    List<Attachment> findByCreationDateAfter(LocalDateTime date);

    @Query("select d from Attachment d where d.employee.id = ?1")
    void deleteAllByEmployeeId(Integer id);

}
