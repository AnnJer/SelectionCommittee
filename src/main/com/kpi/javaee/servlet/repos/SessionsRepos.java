package com.kpi.javaee.servlet.repos;

import com.kpi.javaee.servlet.entities.SessionsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SessionsRepos extends CrudRepository<SessionsEntity, String> {
    Optional<SessionsEntity> findByToken(String token);
}
