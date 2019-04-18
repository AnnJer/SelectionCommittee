package dataAccess.dao;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {

    T get(long id) throws Exception;

    List<T> getAll() throws Exception;

    T save(T t) throws Exception;
    void update(T t) throws Exception;
    void delete(T t) throws Exception;
}
