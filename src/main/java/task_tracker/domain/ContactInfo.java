package task_tracker.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import task_tracker.dto.ContactInfoDto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "contact_info")
@Data
@NoArgsConstructor
public class ContactInfo implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne()
    @JoinColumn(name = "user", referencedColumnName = "id", foreignKey = @ForeignKey(name = "contact_info_fk"))
    private User user;

    private String email;

    public ContactInfoDto mapToDto() {
        return new ContactInfoDto(
                this.id,
                this.address,
                this.phoneNumber,
                this.email
        );
    }
}
