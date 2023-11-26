package egar.domain.document.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DocumentDtoRead {
    private Integer id;

    private String name;

    private Integer pageCount;

    private String path;

    private LocalDateTime creationDate;

    private Integer employeeId;

    public DocumentDtoRead(Integer id, String name, Integer pageCount, String path, LocalDateTime creationDate, Integer employeeId) {
        this.id = id;
        this.name = name;
        this.pageCount = pageCount;
        this.path = path;
        this.creationDate = creationDate;
        this.employeeId = employeeId;
    }
}
