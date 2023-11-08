package egar.repository;

import egar.domain.report.entity.Report;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends BaseRepository<Report, Integer> {

}
