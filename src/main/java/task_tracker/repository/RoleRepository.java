package task_tracker.repository;

import org.springframework.stereotype.Repository;
import task_tracker.domain.Project;
import task_tracker.domain.Role;

import java.util.UUID;

@Repository
public interface RoleRepository extends BaseRepository<Role, UUID> {

//    @Query("select ci from Project ci where ci.user.id = :id")
//    List<Project> findByUserId(UUID id);
//
//    @Query("select ci from Project ci where ci.user.id = ?1")
//    void deleteByUserId(UUID id);

}
