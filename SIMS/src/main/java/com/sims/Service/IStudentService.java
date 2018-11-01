package com.sims.Service;

import com.sims.Entity.TStudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;


/**
 * IDEA真难用
 */
public interface IStudentService {
    /**
     * 获取学生列表
     *
     * @return学生列表
     */
    Page<TStudentEntity> getStudent(Pageable pageable);

    /**
     * 向数据库中保存一条学生信息
     *
     * @param studentEntity 要保存的学生记录
     */
    void saveStudent(TStudentEntity studentEntity);

    /**
     * 根据id修改学生信息
     * @param params
     */
    void modifyStudent(Map<String,Object> params);

    /**
     * 根据id删除学生
     * @param ids
     */
    void removeStudent(String[] ids);
}
