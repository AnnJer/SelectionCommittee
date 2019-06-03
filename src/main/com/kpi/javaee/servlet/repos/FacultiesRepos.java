package com.kpi.javaee.servlet.repos;

import com.kpi.javaee.servlet.entities.FacultyEntity;
import org.springframework.data.repository.CrudRepository;

public interface FacultiesRepos extends CrudRepository<FacultyEntity, Long> {

}
