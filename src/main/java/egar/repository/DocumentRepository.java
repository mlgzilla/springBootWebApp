package egar.repository;

import egar.domain.document.entity.Document;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DocumentRepository extends BaseRepository<Document, Integer>{

    @Query("select d from Document d where ?1 is null or lower(d.name) like ?1")
    List<Document> findByName(String name);

    @Query("select d from Document d where d.creationDate >= cast(?1 as timestamp)")
    List<Document> findByCreationDateBefore(LocalDateTime date);

    @Query("select d from Document d where d.creationDate < cast(?1 as timestamp)")
    List<Document> findByCreationDateAfter(LocalDateTime date);

    @Query("select d from Document d where d.employee.id = ?1")
    void deleteAllByEmployeeId(Integer id);

}
