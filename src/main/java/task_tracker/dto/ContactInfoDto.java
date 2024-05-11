package task_tracker.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ContactInfoDto {
    private UUID id;

    private String address;

    private String phoneNumber;

    private String email;

    public ContactInfoDto(UUID id, String address, String phoneNumber, String email) {
        this.id = id;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
