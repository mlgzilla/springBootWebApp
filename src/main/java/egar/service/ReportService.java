package egar.service;

import egar.domain.report.dto.ReportDtoRead;
import egar.domain.report.entity.Report;
import egar.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    public Optional<ReportDtoRead> findById(Integer id){
        return reportRepository.findById(id).map
                (report -> new ReportDtoRead(
                        report.getId(),
                        report.getName(),
                        report.getDescription(),
                        report.getDateFiled(),
                        report.getTask().getId()
                ));
    }

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }
}
