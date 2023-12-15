package egar.domain.report.entity;

import egar.domain.report.dto.ReportDtoRead;
import egar.domain.task.entity.Task;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "report")
@Data
@NoArgsConstructor
public class Report implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    @Column(name = "date_filed")
    private LocalDateTime dateFiled;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "report_fk_1"))
    private Task task;

    public ReportDtoRead mapToDto() {
        return new ReportDtoRead(
                this.id,
                this.name,
                this.description,
                this.dateFiled,
                this.task.getId()
        );
    }
}
