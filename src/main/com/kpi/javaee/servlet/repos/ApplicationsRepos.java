package com.kpi.javaee.servlet.repos;

import com.kpi.javaee.servlet.entities.ApplicationEntity;
import com.kpi.javaee.servlet.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationsRepos extends CrudRepository<ApplicationEntity, Long> {

    Long countApplicationsEntitiesByUsersByIdUser(UserEntity user);

    Iterable<ApplicationEntity> findAllByUsersByIdUser(UserEntity userEntity);

}
