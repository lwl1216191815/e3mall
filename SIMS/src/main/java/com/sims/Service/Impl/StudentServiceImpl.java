package com.sims.Service.Impl;

import com.sims.Entity.TStudentEntity;
import com.sims.Repository.TStudentRepository;
import com.sims.Service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Service
public class StudentServiceImpl implements IStudentService {
    @Autowired
    private TStudentRepository studentRepository;
    @Override
    public List<TStudentEntity> getStudent() {

        return studentRepository.findAll();
    }

    @Override
    public void saveStudent(TStudentEntity studentEntity) {
        studentRepository.saveAndFlush(studentEntity);
    }

    @Override
    public void modifyStudent(TStudentEntity studentEntity) {
        TStudentEntity tStudentEntity = studentRepository.findOne(studentEntity.getId());
        tStudentEntity.setName(studentEntity.getName());
        tStudentEntity.setSex(studentEntity.getSex());
        tStudentEntity.setRemark(studentEntity.getRemark());
    }
}

