package egar.domain.vacation.entity;

import egar.domain.employee.entity.Employee;
import egar.domain.vacation.dto.VacationDtoRead;
import egar.enums.VacationStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "vacation")
@Data
@NoArgsConstructor
public class Vacation {
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

    private String description;

    @Enumerated(EnumType.STRING)
    private VacationStatus status;

    public VacationDtoRead mapToDto() {
        return new VacationDtoRead(
                this.id,
                this.timeStart,
                this.timeFinish,
                this.employee.getId(),
                this.description,
                this.status
        );
    }
}
