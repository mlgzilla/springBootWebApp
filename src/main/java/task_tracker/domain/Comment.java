package task_tracker.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import task_tracker.dto.CommentDto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String contents;

    @ManyToOne()
    @JoinColumn(name = "author", referencedColumnName = "id", foreignKey = @ForeignKey(name = "document_fk"))
    private User user;

    private LocalDateTime date;

    @ManyToOne()
    @JoinColumn(name = "task_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "document_fk"))
    private Task task;

    public CommentDto mapToDto() {
        return new CommentDto(
                this.id,
                this.contents,
                this.user.getId(),
                this.date,
                this.task.getId()
        );
    }
}
