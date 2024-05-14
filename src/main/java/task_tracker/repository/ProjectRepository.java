package task_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import task_tracker.domain.Project;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {

    @Query("select p from Project p where ?1 is null or lower(p.name) like ?1")
    List<Project> findByName(String name);

}
