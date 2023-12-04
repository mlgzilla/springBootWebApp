package egar.service;

import egar.domain.report.dto.ReportDtoRead;
import egar.domain.report.entity.Report;
import egar.repository.ReportRepository;
import egar.utils.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    public Result<ReportDtoRead> findById(Integer id){
        Report report = reportRepository.findById(id);
        if (report == null)
            return new Result<>(null, "Report was not found");
        else
            return new Result<>(report.mapToDto(), null);
    }

    public Optional<ReportDtoRead> create(Report report){
        try{
            Report savedReport = reportRepository.saveAndFlush(report);
            return Optional.of(savedReport.mapToDto());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<ReportDtoRead> findByTaskId(Integer id){
        return reportRepository.findByTaskId(id).stream().map(Report::mapToDto).collect(Collectors.toList());
    }

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }
}
