package task_tracker.repository;

import org.springframework.data.jpa.repository.Query;
import task_tracker.domain.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends BaseRepository<User, UUID> {

    @Query("select e from User e where ?1 is null or lower(e.name) like ?1")
    List<User> findByName(String name);

    @Query("select e from User e where ?1 is null or lower(e.surename) like ?1")
    List<User> findBySurename(String surename);
}
