package egar.domain.document.entity;

import egar.domain.document.dto.DocumentDtoRead;
import egar.domain.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "document")
@Data
@NoArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "page_count")
    private Integer pageCount;

    private String path;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @ManyToOne()
    @JoinColumn(name = "employee_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "document_fk"))
    private Employee employee;

    public DocumentDtoRead mapToDto(){
        return new DocumentDtoRead(
                this.id,
                this.name,
                this.pageCount,
                this.path,
                this.creationDate,
                this.employee.getId()
        );
    }
}
