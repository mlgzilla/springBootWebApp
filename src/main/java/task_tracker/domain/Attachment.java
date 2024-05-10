package task_tracker.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import task_tracker.dto.AttachmentDto;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "attachments")
@Data
@NoArgsConstructor
public class Attachment implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String name;

    private String path;

    private Integer size;

    @ManyToOne()
    @JoinColumn(name = "uploader", referencedColumnName = "id", foreignKey = @ForeignKey(name = "files_fk"))
    private User user;

    @Column(name = "date_uploaded")
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateUploaded;

    @ManyToOne()
    @JoinColumn(name = "task", referencedColumnName = "id", foreignKey = @ForeignKey(name = "files_fk2"))
    private Task task;

    public AttachmentDto mapToDto() {
        return new AttachmentDto(
                this.id,
                this.name,
                this.path,
                this.size,
                this.user.mapToDto(),
                this.dateUploaded,
                this.task.mapToDto()
        );
    }

    public Attachment(UUID id, String name, String path, Integer size, User user, LocalDateTime dateUploaded, Task task) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.size = size;
        this.user = user;
        this.dateUploaded = dateUploaded;
        this.task = task;
    }
}
