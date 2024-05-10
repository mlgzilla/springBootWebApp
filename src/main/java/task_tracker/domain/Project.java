package task_tracker.domain;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import task_tracker.dto.ProjectDto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Project implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String name;

    @Type(type = "jsonb")
    @Column(name = "tasks", columnDefinition = "jsonb")
    Set<UUID> tasks;

    @Type(type = "jsonb")
    @Column(name = "users", columnDefinition = "jsonb")
    Set<UUID> users;

    public ProjectDto mapToDto() {
        return new ProjectDto(
                this.id,
                this.name,
                this.users,
                this.tasks
        );
    }
}
