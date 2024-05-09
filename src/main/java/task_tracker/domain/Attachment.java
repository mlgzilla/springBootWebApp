package task_tracker.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String name;

    private String path;

    private Integer size;

    @ManyToOne()
    @JoinColumn(name = "uploader", referencedColumnName = "id", foreignKey = @ForeignKey(name = "files_fk"))
    private User user;

    @Column(name = "date_uploaded")
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
                this.user.getId(),
                this.dateUploaded,
                this.task.getId()
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
