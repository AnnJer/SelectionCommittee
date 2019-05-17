package com.kpi.javaee.servlet.repos;

import com.kpi.javaee.servlet.entities.SubjectsEntity;
import org.springframework.data.repository.CrudRepository;

public interface SubjectsRepos extends CrudRepository<SubjectsEntity, Long> {
}
