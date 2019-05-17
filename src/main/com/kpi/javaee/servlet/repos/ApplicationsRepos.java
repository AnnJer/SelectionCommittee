package com.kpi.javaee.servlet.repos;

import com.kpi.javaee.servlet.entities.ApplicationsEntity;
import com.kpi.javaee.servlet.entities.UsersEntity;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationsRepos extends CrudRepository<ApplicationsEntity, Long> {

    Long countApplicationsEntitiesByUsersByIdUser(UsersEntity user);

}
