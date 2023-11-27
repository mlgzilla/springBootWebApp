package egar.service;

import egar.domain.document.dto.DocumentDtoRead;
import egar.domain.document.entity.Document;
import egar.domain.employee.entity.Employee;
import egar.repository.DocumentRepository;
import org.hibernate.id.uuid.UuidGenerator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final String uploadFolder = "D:\\java";

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Optional<DocumentDtoRead> findById(Integer id) {
        return documentRepository.findById(id).map(Document::mapToDto);
    }

    public List<DocumentDtoRead> findByName(String firstName) {
        return documentRepository.findByName(firstName).stream().map(Document::mapToDto).collect(Collectors.toList());
    }

//    public List<DocumentDtoRead> findByCreationDateBefore(LocalDateTime date) {
//        return documentRepository.findByCreationDateBefore(date).stream().map(Document::mapToDto).collect(Collectors.toList());
//    }

//    public List<DocumentDtoRead> findByCreationDateAfter(LocalDateTime date) {
//        return documentRepository.findByCreationDateAfter(date).stream().map(Document::mapToDto).collect(Collectors.toList());
//    }

    public Optional<String> upload(MultipartFile file){
        String[] split = file.getOriginalFilename().split("\\.");
        String ext = split[split.length-1];
        String newPath = uploadFolder + "\\" + UUID.randomUUID() + "." + ext;
        File outputFile = new File(newPath);
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)){
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
            return Optional.of(newPath);
        } catch (IOException e) {
            return Optional.empty();
        }

    }
}
