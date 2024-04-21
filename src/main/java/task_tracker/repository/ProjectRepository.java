package task_tracker.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import task_tracker.domain.Project;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends BaseRepository<Project, UUID> {

//    @Query("select ci from Project ci where ci.user.id = :id")
//    List<Project> findByUserId(UUID id);
//
//    @Query("select ci from Project ci where ci.user.id = ?1")
//    void deleteByUserId(UUID id);

}
