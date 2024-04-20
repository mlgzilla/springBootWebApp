package task_tracker.repository;

import task_tracker.domain.WorkTime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WorkHoursRepository extends BaseRepository<WorkTime, Integer> {

    @Query("select w from WorkTime w where w.employee.id = :id")
    List<WorkTime> findByEmployeeId(Integer id);

    @Query("select w from WorkTime w where w.employee.id = :id and (w.timeStart >= cast(:timeStart as timestamp) or w.timeFinish <= cast(:timeFinish as timestamp))")
    List<WorkTime> findByEmployeeIdInRange(Integer id, LocalDateTime timeStart, LocalDateTime timeFinish);

    @Query("select w from WorkTime w where w.employee.id = ?1")
    void deleteAllByEmployeeId(Integer id);
}
