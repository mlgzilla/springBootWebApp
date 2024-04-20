package task_tracker.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import task_tracker.dto.ContactInfoDto;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "contact_info")
@Data
@NoArgsConstructor
public class ContactInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "document_fk"))
    private User user;

    private String email;

    public ContactInfoDto mapToDto() {
        return new ContactInfoDto(
                this.id,
                this.address,
                this.phoneNumber,
                this.user.getId(),
                this.email
        );
    }
}
