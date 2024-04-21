package task_tracker.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import task_tracker.domain.ContactInfo;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContactInfoRepository extends BaseRepository<ContactInfo, UUID> {

    @Query("select ci from ContactInfo ci where ci.user.id = :id")
    List<ContactInfo> findByUserId(UUID id);

    @Query("select ci from ContactInfo ci where ci.user.id = ?1")
    void deleteByUserId(UUID id);

}
