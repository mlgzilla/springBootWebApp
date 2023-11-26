package egar.service;

import egar.domain.document.dto.DocumentDtoRead;
import egar.domain.document.entity.Document;
import egar.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Optional<DocumentDtoRead> findById(Integer id) {
        return documentRepository.findById(id).map(Document::mapToDto);
    }

    public List<DocumentDtoRead> findByName(String firstName) {
        return documentRepository.findByName(firstName).stream().map(Document::mapToDto).collect(Collectors.toList());
    }

    public List<DocumentDtoRead> findByCreationDateBefore(LocalDateTime date) {
        return documentRepository.findByCreationDateBefore(date).stream().map(Document::mapToDto).collect(Collectors.toList());
    }

    public List<DocumentDtoRead> findByCreationDateAfter(LocalDateTime date) {
        return documentRepository.findByCreationDateAfter(date).stream().map(Document::mapToDto).collect(Collectors.toList());
    }
}
