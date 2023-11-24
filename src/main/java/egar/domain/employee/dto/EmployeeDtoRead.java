package egar.domain.employee.dto;

import egar.enums.ContractType;
import lombok.Data;

@Data
public class EmployeeDtoRead {
    private Integer id;

    private String firstName;

    private String middleName;

    private String secondName;

    private String phoneNumber;

    private Integer cardNumber;

    private ContractType contractType;

    public EmployeeDtoRead(Integer id, String firstName, String middleName, String secondName, String phoneNumber, Integer cardNumber, ContractType contractType) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.secondName = secondName;
        this.phoneNumber = phoneNumber;
        this.cardNumber = cardNumber;
        this.contractType = contractType;
    }
}
