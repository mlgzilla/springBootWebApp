package egar.domain.employee.dto;

import egar.domain.task.entity.Task;
import egar.enums.ContractType;
import lombok.Data;

import java.util.Set;

@Data
public class EmployeeReadDto {
    private Integer id;

    private String firstName;

    private String middleName;

    private String secondName;

    private String phoneNumber;

    private Integer cardNumber;

    private ContractType contractType;

    private Set<Task> tasks;
}
