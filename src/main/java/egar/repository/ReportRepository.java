package egar.repository;

import egar.domain.report.entity.Report;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportRepository extends BaseRepository<Report, Integer> {

    @Query("SELECT r from Report r")
    List<Report> findAll();

    @Query("select r from Report r where r.task.id = :id")
    List<Report> findByTaskId(Integer id);

    @Query("select r from Report r where r.task.id = :id and (r.dateFiled >= cast(:timeStart as timestamp) and r.dateFiled <= cast(:timeFinish as timestamp))")
    List<Report> findByTaskIdInRange(Integer id, LocalDateTime timeStart, LocalDateTime timeFinish);

    @Query("select r from Report r where r.task.id = ?1")
    void deleteAllByTaskId(Integer id);
}
