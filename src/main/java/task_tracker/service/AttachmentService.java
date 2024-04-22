package task_tracker.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import task_tracker.domain.Attachment;
import task_tracker.domain.Task;
import task_tracker.domain.User;
import task_tracker.dto.AttachmentDto;
import task_tracker.repository.AttachmentRepository;
import task_tracker.utils.Result;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final String uploadFolder;

    public AttachmentService(AttachmentRepository attachmentRepository, @Value("${uploadFolder}") String uploadFolder) {
        this.attachmentRepository = attachmentRepository;
        this.uploadFolder = uploadFolder;
    }

    public Result<AttachmentDto> findById(UUID id) {
        try {
            Optional<Attachment> attachment = attachmentRepository.findById(id);
            if (attachment.isEmpty())
                return Result.error("Attachment was not found", "404");
            else
                return Result.ok(attachment.get().mapToDto());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding attachment", "500");
        }
    }

    public Result<List<AttachmentDto>> findByTaskId(UUID taskId) {
        try {
            List<Attachment> attachments = attachmentRepository.findByTaskId(taskId);
            return Result.ok(attachments.stream().map(Attachment::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding attachments for task", "500");
        }
    }

    public Result<String> upload(MultipartFile file, UUID userId, UUID taskId) {
        String[] split = file.getOriginalFilename().split("\\.");
        String ext = split[split.length - 1];
        String newPath = uploadFolder + "/" + UUID.randomUUID() + "." + ext;
        File outputFile = new File(newPath);
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            User user = new User();
            user.setId(userId);
            Task task = new Task();
            task.setId(taskId);
            outputStream.write(file.getBytes());
            Attachment attachment = new Attachment(
                    UUID.randomUUID(),
                    file.getOriginalFilename(),
                    newPath,
                    1,
                    user,
                    LocalDateTime.now(),
                    task);
            attachmentRepository.saveAndFlush(attachment);
            outputStream.close();
            return Result.ok(newPath);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to upload file", "500");
        }
    }

    public Result<byte[]> getFile(String path) {
        FileInputStream fis;
        try {
            fis = new FileInputStream(path);
            return Result.ok(fis.readAllBytes());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to prepare file", "500");
        }
    }

    public Result<String> delete(UUID id) {
        try {
            attachmentRepository.deleteById(id);
            return Result.ok("Delete ok");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to delete report", "500");
        }
    }
}
