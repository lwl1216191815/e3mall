package com.sims.Repository;

import com.sims.Entity.TStudentEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;

/**
 * 学生信息
 * @author 龙帅
 */
@Repository
public class TStudentRepositoryImpl {
    @PersistenceContext
    private EntityManager entityManager;

    public void updateById(Map<String,Object> params){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<TStudentEntity> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(TStudentEntity.class);
        Root<TStudentEntity> root = criteriaUpdate.from(TStudentEntity.class);
        for(Map.Entry<String,Object> entry : params.entrySet()){
            criteriaUpdate.set(entry.getKey(),entry.getValue());
        }
        Predicate predicate = criteriaBuilder.equal(root.get("id"), params.get("id").toString());
        criteriaUpdate.where(predicate);
        Query query = entityManager.createQuery(criteriaUpdate);
        int i = query.executeUpdate();
        System.out.println(i);
    }
}
