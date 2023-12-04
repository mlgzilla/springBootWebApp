package egar.repository;

import egar.utils.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends Repository<T, ID> {
    <S extends T> S saveAndFlush(S entity);

    T findById(ID id);

    void deleteById(ID id);

    Integer count();
}
