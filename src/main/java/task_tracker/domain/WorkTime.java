package task_tracker.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import task_tracker.dto.WorkTimeDto;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "work_time")
@Data
@NoArgsConstructor
public class WorkTime implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "time_start")
    private LocalDateTime timeStart;

    @Column(name = "time_finish")
    private LocalDateTime timeFinish;

    @ManyToOne()
    @JoinColumn(name = "user", referencedColumnName = "id", foreignKey = @ForeignKey(name = "work_hours_fk"))
    private User user;

    private String comment;

    public WorkTimeDto mapToDto() {
        return new WorkTimeDto(
                this.id,
                this.timeStart,
                this.timeFinish,
                this.user.getId(),
                this.comment
        );
    }
}
