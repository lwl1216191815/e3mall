package com.sims.Repository;

import com.sims.Entity.TStudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Map;

public interface TStudentRepository extends JpaRepository<TStudentEntity,String> {
    /**
     * 根据ID动态更新学生信息
     * @param params
     */
    @Modifying
   void updateById(Map<String,Object> params);
}


