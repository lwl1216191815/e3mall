package com.sims.Service.Impl;

import com.sims.Entity.TStudentEntity;
import com.sims.Repository.TStudentRepository;
import com.sims.Service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Transactional
@Service
public class StudentServiceImpl implements IStudentService {
    @Autowired
    private TStudentRepository studentRepository;
    @Override
    public Page<TStudentEntity> getStudent(Pageable pageable) {

        return studentRepository.findAll(pageable);
    }

    @Override
    public void saveStudent(TStudentEntity studentEntity) {
        studentRepository.saveAndFlush(studentEntity);
    }

    @Override
    public void modifyStudent(Map<String,Object> params) {
        studentRepository.updateById(params);
    }

    @Override
    public void removeStudent(String[] ids) {
        for (int i = 0 ; i< ids.length; i++ ){
            studentRepository.delete(ids[i]);
        }
    }
}

