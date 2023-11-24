package egar.domain.employee.entity;

import egar.domain.employee.dto.EmployeeDtoRead;
import egar.domain.task.entity.Task;
import egar.enums.ContractType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "employee")
@Data
@NoArgsConstructor
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "card_number")
    private Integer cardNumber;

    @Column(name = "contract_type")
    @Enumerated(EnumType.STRING)
    private ContractType contractType;

    @OneToMany(mappedBy = "employee")
    private Set<Task> tasks;

    public EmployeeDtoRead mapToDto(){
       return new EmployeeDtoRead(
                this.id,
                this.firstName,
                this.middleName,
                this.secondName,
                this.phoneNumber,
                this.cardNumber,
                this.contractType
        );
    }
}
