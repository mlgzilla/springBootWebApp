package task_tracker.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import task_tracker.dto.AttachmentDto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "attachments")
@Data
@NoArgsConstructor
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String name;

    private String path;

    private Integer size;

    @ManyToOne()
    @JoinColumn(name = "uploader", referencedColumnName = "id", foreignKey = @ForeignKey(name = "document_fk"))
    private User user;

    @Column(name = "date_uploaded")
    private LocalDateTime dateUploaded;

    @ManyToOne()
    @JoinColumn(name = "task_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "document_fk"))
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
}
