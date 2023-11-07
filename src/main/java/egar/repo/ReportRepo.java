package egar.repo;

import egar.domain.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepo extends BaseRepo<Report, Integer> {

}
