package egar.domain.work_hours.entity;

import egar.domain.employee.entity.Employee;
import egar.domain.work_hours.dto.WorkHoursDtoRead;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "work_hours")
@Data
@NoArgsConstructor
public class WorkHours implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "time_start")
    private LocalDateTime timeStart;

    @Column(name = "time_finish")
    private LocalDateTime timeFinish;

    @ManyToOne()
    @JoinColumn(name = "employee_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "work_hours_fk"))
    private Employee employee;

    private String comment;

    public WorkHoursDtoRead mapToDto(){
        return new WorkHoursDtoRead(
                this.id,
                this.timeStart,
                this.timeFinish,
                this.employee.getId(),
                this.comment
        );
    }
}
