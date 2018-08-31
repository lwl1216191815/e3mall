package com.sims.Service.Impl;

import com.sims.Entity.TStudentEntity;
import com.sims.Repository.IStudentRepository;
import com.sims.Service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StudentServiceImpl implements IStudentService {
    @Autowired
    private IStudentRepository studentRepository;
    @Override
    public List<TStudentEntity> getStudent() {

        return studentRepository.findAll();
    }

    @Override
    public void saveStudent(TStudentEntity studentEntity) {

        studentRepository.save(studentEntity);
    }
}

