package task_tracker.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import task_tracker.dto.ReportDto;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reports")
@Data
@NoArgsConstructor
public class Report implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task", referencedColumnName = "id", foreignKey = @ForeignKey(name = "reports_fk"))
    private Task task;

    @Column(name = "date_filed")
    private LocalDateTime dateFiled;

    public ReportDto mapToDto() {
        return new ReportDto(
                this.id,
                this.name,
                this.description,
                this.dateFiled,
                this.task.getId()
        );
    }
}
