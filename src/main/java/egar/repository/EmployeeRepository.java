package egar.repository;

import egar.domain.employee.entity.Employee;
import egar.enums.ContractType;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmployeeRepository extends BaseRepository<Employee, Integer> {
    Optional<Employee> findById(Integer id);

    Optional<Employee> findByContractType(ContractType contractType);
}
