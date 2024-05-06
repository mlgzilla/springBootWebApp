package task_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task_tracker.domain.Role;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

//    @Query("select ci from Project ci where ci.user.id = :id")
//    List<Project> findByUserId(UUID id);
//
//    @Query("select ci from Project ci where ci.user.id = ?1")
//    void deleteByUserId(UUID id);

}
