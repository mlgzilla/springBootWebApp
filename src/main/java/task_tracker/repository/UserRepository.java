package task_tracker.repository;

import org.springframework.data.jpa.repository.Query;
import task_tracker.domain.User;
import task_tracker.enums.ContractType;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends BaseRepository<User, UUID> {

    @Query("select e from User e where ?1 is null or lower(e.name) like ?1")
    List<User> findByFirstName(String firstName);

    @Query("select e from User e where ?1 is null or lower(e.surename) like ?1")
    List<User> findByMiddleName(String middleName);
}
