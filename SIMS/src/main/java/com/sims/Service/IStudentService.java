package com.sims.Service;

import com.sims.Entity.TStudentEntity;

import java.util.List;

/**
 * IDEA真难用
 */
public interface IStudentService {
    /**
     * 获取学生列表
     *
     * @return学生列表
     */
    List<TStudentEntity> getStudent();

    /**
     * 向数据库中保存一条学生信息
     *
     * @param studentEntity 要保存的学生记录
     */
    void saveStudent(TStudentEntity studentEntity);
}
