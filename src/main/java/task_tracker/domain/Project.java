package task_tracker.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import task_tracker.dto.ProjectDto;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String name;

    public ProjectDto mapToDto() {
        return new ProjectDto(
                this.id,
                this.name
        );
    }
}
