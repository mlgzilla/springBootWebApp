package task_tracker.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
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
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "time_start")
    private LocalDateTime timeStart;

    @Column(name = "time_finish")
    private LocalDateTime timeFinish;

    @ManyToOne()
    @JoinColumn(name = "user", referencedColumnName = "id", foreignKey = @ForeignKey(name = "work_time_fk"))
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
