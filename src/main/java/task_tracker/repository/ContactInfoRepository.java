package task_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import task_tracker.domain.ContactInfo;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContactInfoRepository extends JpaRepository<ContactInfo, UUID> {

    @Query("select ci from ContactInfo ci where ci.user.id = :id")
    List<ContactInfo> findByUserId(UUID id);

    @Modifying
    @Transactional
    @Query("delete from ContactInfo where user.id = ?1")
    void deleteAllByUserId(UUID id);

}
