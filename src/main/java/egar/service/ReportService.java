package egar.service;

import egar.domain.report.dto.ReportDtoRead;
import egar.domain.report.entity.Report;
import egar.repository.ReportRepository;
import egar.utils.Result;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    public Result<ReportDtoRead> findById(Integer id) {
        try {
            Optional<Report> report = reportRepository.findById(id);
            if (report.isEmpty())
                return Result.error("Report was not found", "404");
            else
                return Result.ok(report.get().mapToDto());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding Reports", "500");
        }
    }

    public Result<List<ReportDtoRead>> findByTaskIdInRange(Integer id, LocalDateTime timeStart, LocalDateTime timeFinish) {
        try {
            List<Report> reports = reportRepository.findByTaskIdInRange(id, timeStart, timeFinish);
            if (reports.isEmpty())
                return Result.error("Reports by task id in range were not found", "404");
            else
                return Result.ok(reports.stream().map(Report::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding Reports by task id in range", "500");
        }
    }

    public Result<List<ReportDtoRead>> findByTaskId(Integer id) {
        try {
            List<Report> reports = reportRepository.findByTaskId(id);
            if (reports.isEmpty())
                return Result.error("Reports by task id were not found", "404");
            else
                return Result.ok(reports.stream().map(Report::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding Reports by task id", "500");
        }
    }

    public Result<ReportDtoRead> create(Report report) {
        try {
            Report savedReport = reportRepository.saveAndFlush(report);
            return Result.ok(savedReport.mapToDto());
        } catch (Exception e) {
            return Result.error("Error creating Reports", "500");
        }
    }

    public Result<String> update(Integer id, ReportDtoRead reportDto) {
        try {
            Optional<Report> reportRead = reportRepository.findById(id);
            if (reportRead.isEmpty())
                return Result.error("Report was not found", "404");
            Report report = reportRead.get();
            report.setName(reportDto.getName());
            report.setDescription(reportDto.getDescription());
            reportRepository.saveAndFlush(report);
            return Result.ok("Update ok");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to update report", "500");
        }
    }

    public Result<String> delete(Integer id) {
        try {
            reportRepository.deleteById(id);
            return Result.ok("Delete ok");
        } catch (Exception e) {
            return Result.error("Failed to delete file", "500");
        }
    }

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }
}
