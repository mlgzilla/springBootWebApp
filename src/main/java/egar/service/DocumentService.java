package egar.service;

import egar.domain.document.dto.DocumentDtoRead;
import egar.domain.document.entity.Document;
import egar.domain.employee.entity.Employee;
import egar.repository.DocumentRepository;
import egar.utils.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final String uploadFolder;

    public DocumentService(DocumentRepository documentRepository, @Value("${uploadFolder}") String uploadFolder) {
        this.documentRepository = documentRepository;
        this.uploadFolder = uploadFolder;
    }

    public Result<DocumentDtoRead> findById(Integer id) {
        try {
            Optional<Document> document = documentRepository.findById(id);
            if (document.isEmpty())
                return Result.error("Document was not found", "404");
            else
                return Result.ok(document.get().mapToDto());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding document", "500");
        }
    }

    public Result<List<DocumentDtoRead>> findByName(String name) {
        try {
            List<Document> document = documentRepository.findByName(name + '%');
            if (document.isEmpty())
                return Result.error("Documents by name were not found", "404");
            else
                return Result.ok(document.stream().map(Document::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding documents by name", "500");
        }
    }

    public Result<List<DocumentDtoRead>> findByCreationDateBefore(LocalDateTime date) {
        try {
            List<Document> document = documentRepository.findByCreationDateBefore(date);
            if (document.isEmpty())
                return Result.error("Documents by name were not found", "404");
            else
                return Result.ok(document.stream().map(Document::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding documents by name", "500");
        }
    }

    public Result<List<DocumentDtoRead>> findByCreationDateAfter(LocalDateTime date) {
        try {
            List<Document> document = documentRepository.findByCreationDateAfter(date);
            if (document.isEmpty())
                return Result.error("Documents by name were not found", "404");
            else
                return Result.ok(document.stream().map(Document::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding documents by name", "500");
        }
    }

    public Result<String> upload(MultipartFile file) {
        String[] split = file.getOriginalFilename().split("\\.");
        String ext = split[split.length - 1];
        String newPath = uploadFolder + "/" + UUID.randomUUID() + "." + ext;
        File outputFile = new File(newPath);
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            Employee employee = new Employee();
            employee.setId(1);
            outputStream.write(file.getBytes());
            Document document = new Document(
                    0,
                    file.getOriginalFilename(),
                    1,
                    newPath,
                    LocalDateTime.now(),
                    employee);
            documentRepository.saveAndFlush(document);
            outputStream.close();
            return Result.ok(newPath);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to upload file", "500");
        }
    }

    public Result<byte[]> getFile(String path){
        FileInputStream fis;
        try {
            fis = new FileInputStream(path);
            return Result.ok(fis.readAllBytes());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to prepare file", "500");
        }
    }

    public Result<String> delete(Integer id) {
        try {
            documentRepository.deleteById(id);
            return Result.ok("Delete ok");
        } catch (Exception e) {
            return Result.error("Failed to delete report", "500");
        }
    }
}
