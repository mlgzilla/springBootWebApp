package task_tracker.service;

import task_tracker.domain.Attachment;
import task_tracker.dto.AttachmentDto;
import task_tracker.domain.User;
import task_tracker.repository.AttachmentRepository;
import task_tracker.utils.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
public class DocumentService {
    private final AttachmentRepository attachmentRepository;
    private final String uploadFolder;

    public DocumentService(AttachmentRepository attachmentRepository, @Value("${uploadFolder}") String uploadFolder) {
        this.attachmentRepository = attachmentRepository;
        this.uploadFolder = uploadFolder;
    }

    public Result<AttachmentDto> findById(Integer id) {
        try {
            Optional<Attachment> document = attachmentRepository.findById(id);
            if (document.isEmpty())
                return Result.error("Document was not found", "404");
            else
                return Result.ok(document.get().mapToDto());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding document", "500");
        }
    }

    public Result<List<AttachmentDto>> findByEmployeeId(Integer id) {
        try {
            List<Attachment> attachments = attachmentRepository.findByUserId(id);
            if (attachments.isEmpty())
                return Result.error("Documents by employee were not found", "404");
            else
                return Result.ok(attachments.stream().map(Attachment::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding documents by employee", "500");
        }
    }

    public Result<List<AttachmentDto>> findByName(String name) {
        try {
            List<Attachment> attachment = attachmentRepository.findByName(name + '%');
            if (attachment.isEmpty())
                return Result.error("Documents by name were not found", "404");
            else
                return Result.ok(attachment.stream().map(Attachment::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding documents by name", "500");
        }
    }

    public Result<List<AttachmentDto>> findByCreationDateBefore(LocalDateTime date) {
        try {
            List<Attachment> attachment = attachmentRepository.findByCreationDateBefore(date);
            if (attachment.isEmpty())
                return Result.error("Documents by name were not found", "404");
            else
                return Result.ok(attachment.stream().map(Attachment::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding documents by name", "500");
        }
    }

    public Result<List<AttachmentDto>> findByCreationDateAfter(LocalDateTime date) {
        try {
            List<Attachment> attachment = attachmentRepository.findByCreationDateAfter(date);
            if (attachment.isEmpty())
                return Result.error("Documents by name were not found", "404");
            else
                return Result.ok(attachment.stream().map(Attachment::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding documents by name", "500");
        }
    }

    public Result<String> upload(MultipartFile file, Integer id) {
        String[] split = file.getOriginalFilename().split("\\.");
        String ext = split[split.length - 1];
        String newPath = uploadFolder + "/" + UUID.randomUUID() + "." + ext;
        File outputFile = new File(newPath);
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            User user = new User();
            user.setId(id);
            outputStream.write(file.getBytes());
            Attachment attachment = new Attachment(
                    1,
                    file.getOriginalFilename(),
                    1,
                    newPath,
                    LocalDateTime.now(),
                    user);
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

    public Result<String> update(Integer id, AttachmentDto attachmentDto) {
        try {
            Optional<Attachment> documentRead = attachmentRepository.findById(id);
            if (documentRead.isEmpty())
                return Result.error("Document was not found", "404");
            Attachment attachment = documentRead.get();
            attachment.setName(attachmentDto.getName());
            attachmentRepository.saveAndFlush(attachment);
            return Result.ok("Update ok");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to update document", "500");
        }
    }

    public Result<String> delete(Integer id) {
        try {
            attachmentRepository.deleteById(id);
            return Result.ok("Delete ok");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to delete report", "500");
        }
    }
}
