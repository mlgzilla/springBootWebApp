package egar.repository;

import egar.domain.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends BaseRepository<Report, Integer> {
    Optional<Report> findById(Integer id);

    @Query("SELECT r from Report r")
    Iterable<Report> findAll();


}
