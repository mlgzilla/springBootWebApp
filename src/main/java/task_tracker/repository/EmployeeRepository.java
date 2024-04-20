package task_tracker.repository;

import task_tracker.domain.User;
import task_tracker.enums.ContractType;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends BaseRepository<User, Integer> {

    @Query("select e from User e where ?1 is null or lower(e.name) like ?1")
    List<User> findByFirstName(String firstName);

    @Query("select e from User e where ?1 is null or lower(e.surename) like ?1")
    List<User> findByMiddleName(String middleName);

    @Query("select e from User e where ?1 is null or lower(e.secondName) like ?1")
    List<User> findBySecondName(String secondName);

    List<User> findByContractType(ContractType contractType);
}
