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

    @Query("select w from WorkHours w where w.timeStart >= cast(?1 as timestamp)")
    List<WorkHours> findByTimeStartBefore(LocalDateTime date);

    @Query("select w from WorkHours w where w.timeStart < cast(?1 as timestamp)")
    List<WorkHours> findByTimeStartAfter(LocalDateTime date);

    @Query("select w from WorkHours w where w.timeFinish >= cast(?1 as timestamp)")
    List<WorkHours> findByTimeFinishBefore(LocalDateTime date);

    @Query("select w from WorkHours w where w.timeFinish < cast(?1 as timestamp)")
    List<WorkHours> findByTimeFinishAfter(LocalDateTime date);

}
