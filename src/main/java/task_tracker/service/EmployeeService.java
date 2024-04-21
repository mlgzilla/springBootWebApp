package task_tracker.service;

import task_tracker.dto.UserDto;
import task_tracker.domain.User;
import task_tracker.enums.ContractType;
import task_tracker.repository.*;
import task_tracker.utils.Result;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final UserRepository userRepository;
    private final AttachmentRepository attachmentRepository;
    private final TaskRepository taskRepository;
    private final VacationRepository vacationRepository;
    private final WorkTimeRepository workTimeRepository;

    public EmployeeService(UserRepository userRepository, AttachmentRepository attachmentRepository, TaskRepository taskRepository, VacationRepository vacationRepository, WorkTimeRepository workTimeRepository) {
        this.userRepository = userRepository;
        this.attachmentRepository = attachmentRepository;
        this.taskRepository = taskRepository;
        this.vacationRepository = vacationRepository;
        this.workTimeRepository = workTimeRepository;
    }

    public Result<UserDto> findById(Integer id) {
        try {
            Optional<User> employee = userRepository.findById(id);
            if (employee.isEmpty())
                return Result.error("Employee was not found", "404");
            else
                return Result.ok(employee.get().mapToDto());
        } catch (Exception e) {
            return Result.error("Error finding employee", "500");
        }

    }

    public Result<List<UserDto>> findByFirstName(String firstName) {
        try {
            List<User> users = userRepository.findByFirstName(firstName + '%');
            if (users.isEmpty())
                return Result.error("Employees by first name were not found", "404");
            else
                return Result.ok(users.stream().map(User::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding Employees by first name", "500");
        }
    }

    public Result<List<UserDto>> findByMiddleName(String middleName) {
        try {
            List<User> users = userRepository.findByMiddleName(middleName + '%');
            if (users.isEmpty())
                return Result.error("Employees by middle name were not found", "404");
            else
                return Result.ok(users.stream().map(User::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding Employees by middle name", "500");
        }
    }

    public Result<List<UserDto>> findBySecondName(String secondName) {
        try {
            List<User> users = userRepository.findBySecondName(secondName + '%');
            if (users.isEmpty())
                return Result.error("Employees by second name were not found", "404");
            else
                return Result.ok(users.stream().map(User::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding Employees by second name", "500");
        }
    }

    public Result<List<UserDto>> findByContractType(ContractType contractType) {
        try {
            List<User> users = userRepository.findByContractType(contractType);
            if (users.isEmpty())
                return Result.error("Employees by contract type were not found", "404");
            else
                return Result.ok(users.stream().map(User::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding Employees by contract type", "500");
        }
    }

    public Result<UserDto> create(User user) {
        try {
            User savedUser = userRepository.saveAndFlush(user);
            return Result.ok(savedUser.mapToDto());
        } catch (Exception e) {
            return Result.error("Error creating Employee", "500");
        }
    }

    public Result<String> update(Integer id, UserDto employeeDto) {
        try {
            Optional<User> employeeRead = userRepository.findById(id);
            if (employeeRead.isEmpty())
                return Result.error("Employee was not found", "404");
            User user = employeeRead.get();
            user.setName(employeeDto.getFirstName());
            user.setSecondName(employeeDto.getSecondName());
            user.setCardNumber(employeeDto.getCardNumber());
            user.setPhoneNumber(employeeDto.getPhoneNumber());
            user.setContractType(employeeDto.getContractType());
            userRepository.saveAndFlush(user);
            return Result.ok("Update ok");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to update employee", "500");
        }
    }

    public Result<String> delete(Integer id) {
        try {
            attachmentRepository.deleteAllByUserId(id);
            taskRepository.deleteAllByEmployeeId(id);
            vacationRepository.deleteAllByEmployeeId(id);
            workTimeRepository.deleteAllByEmployeeId(id);
            userRepository.deleteById(id);
            return Result.ok("Delete ok");
        } catch (Exception e) {
            return Result.error("Failed to delete employee", "500");
        }
    }
}
