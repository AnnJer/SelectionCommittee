package com.kpi.javaee.servlet.repos;

import com.kpi.javaee.servlet.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsersRepos extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByLogin(String login);
}
