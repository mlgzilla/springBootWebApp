package task_tracker.repository;

import task_tracker.domain.Report;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReportRepository extends BaseRepository<Report, UUID> {

    @Query("SELECT r from Report r")
    List<Report> findAll();

    @Query("select r from Report r where r.task.id = :id")
    List<Report> findByTaskId(UUID id);

    @Query("select r from Report r where r.task.id = :id and (r.dateFiled >= cast(:timeStart as timestamp) and r.dateFiled <= cast(:timeFinish as timestamp))")
    List<Report> findByTaskIdInRange(UUID id, LocalDateTime timeStart, LocalDateTime timeFinish);

    @Query("select r from Report r where r.task.id = ?1")
    void deleteAllByTaskId(UUID id);
}
