package com.kpi.javaee.servlet.repos;

import com.kpi.javaee.servlet.entities.UsersEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsersRepos extends CrudRepository<UsersEntity, Long> {
    Optional<UsersEntity> findByLogin(String login);
}
