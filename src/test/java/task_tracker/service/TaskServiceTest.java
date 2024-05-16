package task_tracker.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import task_tracker.domain.ContactInfo;
import task_tracker.domain.Project;
import task_tracker.domain.Task;
import task_tracker.domain.User;
import task_tracker.dto.TaskDto;
import task_tracker.repository.AttachmentRepository;
import task_tracker.repository.CommentRepository;
import task_tracker.repository.ProjectRepository;
import task_tracker.repository.TaskRepository;
import task_tracker.utils.Result;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    public void testFindById_TaskFound() {
        UUID taskId = UUID.randomUUID();
        Task task = new Task();
        task.setId(taskId);
        User user = new User();
        user.setContactInfo(new ContactInfo());
        task.setUser(user);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        Result<TaskDto> result = taskService.findById(taskId);

        Assertions.assertEquals(taskId, result.getObject().getId());
    }

    @Test
    public void testFindById_TaskNotFound() {
        UUID taskId = UUID.randomUUID();
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        Result<TaskDto> result = taskService.findById(taskId);

        assertEquals("Task was not found", result.getMessage());
        assertEquals("404", result.getCode());
    }

    @Test
    public void testFindByUserId() {
        UUID userId = UUID.randomUUID();
        List<Task> tasks = new ArrayList<>();
        when(taskRepository.findByUserId(userId)).thenReturn(tasks);

        Result<List<TaskDto>> result = taskService.findByUserId(userId);

        assertEquals(0, result.getObject().size());
    }

    @Test
    public void testFindByName() {
        String name = "Test";
        List<Task> tasks = new ArrayList<>();
        when(taskRepository.findByName(name + '%')).thenReturn(tasks);

        Result<List<TaskDto>> result = taskService.findByName(name);

        assertEquals(0, result.getObject().size());
    }

    @Test
    public void testCreate() {
        Task task = new Task();
        task.setId(UUID.randomUUID());
        task.setProjectId(UUID.randomUUID());

        Project project = new Project();
        project.setId(UUID.randomUUID());
        project.setTasks(new HashSet<>());

        when(taskRepository.saveAndFlush(task)).thenReturn(task);
        when(projectRepository.findById(task.getProjectId())).thenReturn(Optional.of(project));

        Result<String> result = taskService.create(task);

        assertEquals("OK", result.getObject());
        verify(projectRepository).saveAndFlush(project);
    }

    @Test
    public void testUpdate_TaskFound() {
        UUID taskId = UUID.randomUUID();
        TaskDto taskDto = new TaskDto();
        taskDto.setName("Updated Task");
        Task task = new Task();
        task.setId(taskId);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        Result<String> result = taskService.update(taskId, taskDto);

        assertEquals("Update ok", result.getObject());
    }

    @Test
    public void testUpdate_TaskNotFound() {
        UUID taskId = UUID.randomUUID();
        TaskDto taskDto = new TaskDto();

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        Result<String> result = taskService.update(taskId, taskDto);

        assertEquals("Task was not found", result.getMessage());
        assertEquals("404", result.getCode());
    }

    @Test
    public void testDelete_TaskFound() {
        UUID taskId = UUID.randomUUID();
        Task task = new Task();
        task.setId(taskId);
        task.setProjectId(UUID.randomUUID());

        Project project = new Project();
        project.setId(UUID.randomUUID());
        project.setTasks(new HashSet<>(Collections.singletonList(taskId)));

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(projectRepository.findById(task.getProjectId())).thenReturn(Optional.of(project));

        Result<String> result = taskService.delete(taskId);

        assertEquals("Delete ok", result.getObject());
        verify(projectRepository).saveAndFlush(project);
    }

    @Test
    public void testDelete_TaskNotFound() {
        UUID taskId = UUID.randomUUID();

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        Result<String> result = taskService.delete(taskId);

        assertEquals("Failed to delete task", result.getMessage());
        assertEquals("500", result.getCode());
    }
}