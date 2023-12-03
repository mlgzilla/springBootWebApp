package egar.repository;

import egar.domain.vacation.entity.Vacation;
import egar.enums.VacationStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VacationRepository extends BaseRepository<Vacation, Integer> {
    @Query("select v from Vacation v where v.employee.id = :id")
    List<Vacation> findByEmployeeId(Integer id);

    @Query("select v from Vacation v where v.employee.id = :id and v.status = :status")
    List<Vacation> findByEmployeeIdAndStatus(Integer id, VacationStatus status);

    @Query("select v from Vacation v where v.employee.id = :id and v.status = :status and (v.timeStart <= cast(:timeStart as timestamp) or v.timeFinish >= cast(:timeFinish as timestamp))")
    List<Vacation> findByEmployeeIdAndStatusInRange(Integer id, VacationStatus status, LocalDateTime timeStart, LocalDateTime timeFinish);

    @Query("select v from Vacation v where v.timeStart >= cast(?1 as timestamp)")
    List<Vacation> findByTimeStartBefore(LocalDateTime date);

    @Query("select v from Vacation v where v.timeStart < cast(?1 as timestamp)")
    List<Vacation> findByTimeStartAfter(LocalDateTime date);

    @Query("select v from Vacation v where v.timeFinish >= cast(?1 as timestamp)")
    List<Vacation> findByTimeFinishBefore(LocalDateTime date);

    @Query("select v from Vacation v where v.timeFinish < cast(?1 as timestamp)")
    List<Vacation> findByTimeFinishAfter(LocalDateTime date);
}
