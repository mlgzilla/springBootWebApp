package egar.service;

import egar.domain.report.dto.ReportDtoRead;
import egar.domain.report.entity.Report;
import egar.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            Report savedReport = reportRepository.saveAndFlush(report);
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

    public List<ReportDtoRead> findByTaskId(Integer id){
        return reportRepository.findByTaskId(id).stream().map(report -> new ReportDtoRead(
                report.getId(),
                report.getName(),
                report.getDescription(),
                report.getDateFiled(),
                report.getTask().getId()
        )).collect(Collectors.toList());
    }

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }
}
