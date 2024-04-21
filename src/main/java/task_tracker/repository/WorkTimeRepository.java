package task_tracker.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import task_tracker.domain.WorkTime;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface WorkTimeRepository extends BaseRepository<WorkTime, UUID> {

    @Query("select w from WorkTime w where w.user.id = :id")
    List<WorkTime> findByEmployeeId(UUID id);

    @Query("select w from WorkTime w where w.user.id = :id and (w.timeStart >= cast(:timeStart as timestamp) or w.timeFinish <= cast(:timeFinish as timestamp))")
    List<WorkTime> findByEmployeeIdInRange(UUID id, LocalDateTime timeStart, LocalDateTime timeFinish);

    @Query("select w from WorkTime w where w.user.id = ?1")
    void deleteAllByEmployeeId(UUID id);
}
