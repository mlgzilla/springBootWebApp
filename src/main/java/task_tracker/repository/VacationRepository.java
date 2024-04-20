package task_tracker.repository;

import task_tracker.enums.VacationStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VacationRepository extends BaseRepository<Vacation, Integer> {
    @Query("select v from Vacation v where v.employee.id = :id")
    List<Vacation> findByEmployeeId(Integer id);

    @Query("select v from Vacation v where v.status = :status")
    List<Vacation> findByStatus(VacationStatus status);

    @Query("select v from Vacation v where v.employee.id = :id and v.status = :status and (v.timeStart <= cast(:timeStart as timestamp) or v.timeFinish >= cast(:timeFinish as timestamp))")
    List<Vacation> findByEmployeeIdAndStatusInRange(Integer id, VacationStatus status, LocalDateTime timeStart, LocalDateTime timeFinish);

    @Query("select v from Vacation v where v.employee.id = ?1")
    void deleteAllByEmployeeId(Integer id);
}
