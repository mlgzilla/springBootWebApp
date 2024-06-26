package task_tracker.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import task_tracker.dto.CommentDto;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
public class Comment implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String contents;

    @ManyToOne()
    @JoinColumn(name = "author", referencedColumnName = "id", foreignKey = @ForeignKey(name = "news_fk"))
    private User user;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime date;

    @ManyToOne()
    @JoinColumn(name = "task", referencedColumnName = "id", foreignKey = @ForeignKey(name = "comment_fk"))
    private Task task;

    public CommentDto mapToDto() {
        return new CommentDto(
                this.id,
                this.contents,
                this.user.mapToDto(),
                this.date,
                this.task.getId()
        );
    }
}
