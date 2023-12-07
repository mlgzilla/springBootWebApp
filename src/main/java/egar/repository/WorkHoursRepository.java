package egar.repository;

import egar.domain.work_hours.entity.WorkHours;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WorkHoursRepository extends BaseRepository<WorkHours, Integer> {

    @Query("select w from WorkHours w where w.employee.id = :id")
    List<WorkHours> findByEmployeeId(Integer id);

    @Query("select w from WorkHours w where w.employee.id = :id and (w.timeStart >= cast(:timeStart as timestamp) or w.timeFinish <= cast(:timeFinish as timestamp))")
    List<WorkHours> findByEmployeeIdInRange(Integer id, LocalDateTime timeStart, LocalDateTime timeFinish);

    @Query("select w from WorkHours w where w.employee.id = ?1")
    void deleteAllByEmployeeId(Integer id);
}
