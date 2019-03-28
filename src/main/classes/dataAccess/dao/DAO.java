package dataAccess.dao;

import java.util.List;

public interface DAO<T> {

    T get(long id);

    List<T> getAll();

    T save(T t) throws Exception;
    void update(T t) throws Exception;
    void delete(T t) throws Exception;
}
