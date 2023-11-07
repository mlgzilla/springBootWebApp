package egar.repo;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@NoRepositoryBean
public interface BaseRepo<T, ID> extends Repository<T, ID> {
    <S extends T> S save(S entity);

    Optional<T> findById(ID id);

    Iterable<T> findAll();

    void deleteById(ID id);
}
