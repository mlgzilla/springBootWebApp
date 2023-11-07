package egar.repo;

import egar.domain.report.entity.Report;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends BaseRepo<Report, Integer> {

}
