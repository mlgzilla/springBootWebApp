package egar.repository;

import egar.domain.employee.entity.Employee;
import egar.enums.ContractType;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends BaseRepository<Employee, Integer> {
    Optional<Employee> findById(Integer id);

    List<Employee> findByContractType(ContractType contractType);
}
