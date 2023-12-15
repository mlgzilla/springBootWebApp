package egar.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends Repository<T, ID> {
    <S extends T> S saveAndFlush(S entity);

    Optional<T> findById(ID id);

    void deleteById(ID id);

    Integer count();
}
