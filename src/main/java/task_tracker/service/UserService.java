package task_tracker.service;

import org.springframework.stereotype.Service;
import task_tracker.domain.User;
import task_tracker.dto.UserDto;
import task_tracker.enums.ContractType;
import task_tracker.repository.AttachmentRepository;
import task_tracker.repository.TaskRepository;
import task_tracker.repository.UserRepository;
import task_tracker.repository.WorkTimeRepository;
import task_tracker.utils.Result;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AttachmentRepository attachmentRepository;
    private final TaskRepository taskRepository;
    private final WorkTimeRepository workTimeRepository;

    public UserService(UserRepository userRepository, AttachmentRepository attachmentRepository, TaskRepository taskRepository, WorkTimeRepository workTimeRepository) {
        this.userRepository = userRepository;
        this.attachmentRepository = attachmentRepository;
        this.taskRepository = taskRepository;
        this.workTimeRepository = workTimeRepository;
    }

    public Result<UserDto> findById(UUID id) {
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

    public Result<List<UserDto>> findByName(String firstName) {
        try {
            List<User> users = userRepository.findByName(firstName + '%');
            if (users.isEmpty())
                return Result.error("Employees by first name were not found", "404");
            else
                return Result.ok(users.stream().map(User::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding Employees by first name", "500");
        }
    }

    public Result<List<UserDto>> findBySurename(String middleName) {
        try {
            List<User> users = userRepository.findBySurename(middleName + '%');
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

    public Result<String> update(UUID id, UserDto employeeDto) {
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

    public Result<String> delete(UUID id) {
        try {
            attachmentRepository.deleteAllByUserId(id);
            taskRepository.deleteAllByUserId(id);
            vacationRepository.deleteAllByEmployeeId(id);
            workTimeRepository.deleteAllByEmployeeId(id);
            userRepository.deleteById(id);
            return Result.ok("Delete ok");
        } catch (Exception e) {
            return Result.error("Failed to delete employee", "500");
        }
    }
}
