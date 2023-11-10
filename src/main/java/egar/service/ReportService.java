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

    public Optional<ReportDtoRead> create(Report report){
        try{
            Report savedReport = reportRepository.save(report);
            return Optional.of(new ReportDtoRead(
                    savedReport.getId(),
                    savedReport.getName(),
                    savedReport.getDescription(),
                    savedReport.getDateFiled(),
                    savedReport.getTask().getId()
            ));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }
}
