package egar.service;

import egar.domain.report.entity.Report;
import egar.repository.ReportRepository;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    public Report findById(Integer id){
        return reportRepository.findById(id);
    }

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }
}
