package egar.repository;

import egar.domain.employee.entity.Employee;
import egar.enums.ContractType;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends BaseRepository<Employee, Integer> {
    Optional<Employee> findById(Integer id);

    @Query("select e from Employee e where ?1 is null or lower(e.firstName) like ?1")
    List<Employee> findByFirstName(String firstName);

    @Query("select e from Employee e where ?1 is null or lower(e.middleName) like ?1")
    List<Employee> findByMiddleName(String middleName);

    @Query("select e from Employee e where ?1 is null or lower(e.secondName) like ?1")
    List<Employee> findBySecondName(String secondName);

    List<Employee> findByContractType(ContractType contractType);
}
