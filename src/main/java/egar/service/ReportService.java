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
        return reportRepository.findById(id).map(Report::mapToDto);
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
