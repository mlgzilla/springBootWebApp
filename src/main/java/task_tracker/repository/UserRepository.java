package task_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import task_tracker.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("select u from User u where ?1 is null or lower(u.name) like ?1")
    List<User> findByName(String name);

    @Query("select u from User u where u.login = ?1")
    Optional<User> findByLogin(String login);

    @Query("select u from User u where ?1 is null or lower(u.surename) like ?1")
    List<User> findBySurename(String surename);
}
