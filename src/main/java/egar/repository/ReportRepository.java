package egar.repository;

import egar.domain.report.dto.ReportDtoRead;
import egar.domain.report.entity.Report;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends BaseRepository<Report, Integer> {
    ReportDtoRead findById(Integer id);

    @Query("SELECT r from Report r")
    Iterable<ReportDtoRead> findAll();


}
