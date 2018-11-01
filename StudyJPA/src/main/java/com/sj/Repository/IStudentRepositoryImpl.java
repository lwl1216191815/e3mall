package com.sj.Repository;


import com.sj.Entity.StudentEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Repository
public class IStudentRepositoryImpl {
    @PersistenceContext
    private EntityManager entityManager;
    public Integer updateStudentById(StudentEntity entity){
     return 0;
    }

}
