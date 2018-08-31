package com.sims.Repository;

import com.sims.Entity.TStudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 */
public interface IStudentRepository extends JpaRepository<TStudentEntity,String>{

}
