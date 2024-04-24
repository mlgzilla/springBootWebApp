package task_tracker.service;

import org.springframework.stereotype.Service;
import task_tracker.domain.ContactInfo;
import task_tracker.dto.ContactInfoDto;
import task_tracker.repository.ContactInfoRepository;
import task_tracker.utils.Result;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ContactInfoService {
    private final ContactInfoRepository contactInfoRepository;

    public Result<List<ContactInfoDto>> findByUserId(UUID id) {
        try {
            List<ContactInfo> contactInfo = contactInfoRepository.findByUserId(id);
            if (contactInfo.isEmpty())
                return Result.error("ContactInfo by user id were not found", "404");
            else
                return Result.ok(contactInfo.stream().map(ContactInfo::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding ContactInfo by user id", "500");
        }
    }

    public Result<ContactInfoDto> create(ContactInfo contactInfo) {
        try {
            ContactInfo savedContactInfo = contactInfoRepository.saveAndFlush(contactInfo);
            return Result.ok(savedContactInfo.mapToDto());
        } catch (Exception e) {
            return Result.error("Error creating ContactInfo", "500");
        }
    }

    public Result<String> update(UUID id, ContactInfoDto contactInfoDto) {
        try {
            Optional<ContactInfo> contactInfoRead = contactInfoRepository.findById(id);
            if (contactInfoRead.isEmpty())
                return Result.error("ContactInfo was not found", "404");
            ContactInfo contactInfo = new ContactInfo();
            contactInfo.setAddress(contactInfoDto.getAddress());
            contactInfo.setEmail(contactInfoDto.getEmail());
            contactInfo.setPhoneNumber(contactInfoDto.getPhoneNumber());
            contactInfoRepository.saveAndFlush(contactInfo);
            return Result.ok("Update ok");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to update contactInfo", "500");
        }
    }

    public Result<String> delete(UUID id) {
        try {
            contactInfoRepository.deleteById(id);
            return Result.ok("Delete ok");
        } catch (Exception e) {
            return Result.error("Failed to delete contactInfo", "500");
        }
    }

    public ContactInfoService(ContactInfoRepository contactInfoRepository) {
        this.contactInfoRepository = contactInfoRepository;
    }
}
