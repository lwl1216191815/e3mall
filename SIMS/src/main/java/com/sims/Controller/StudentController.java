package com.sims.Controller;


import com.alibaba.fastjson.JSONObject;
import com.sims.Entity.TStudentEntity;
import com.sims.Service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 我可爱吧
 */
@RestController
@RequestMapping(value = "/student/")
public class StudentController {
    @Autowired
    private IStudentService studentService;

    /**
     * 获取所有的学生信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getStudent.action")
    public List<TStudentEntity> getStudent(){
        List<TStudentEntity> entityList = studentService.getStudent();
        return entityList;
    }

    @RequestMapping(value = "saveStudent.action")
    public void saveStudent(String json){
        TStudentEntity studentEntity = JSONObject.parseObject(json, TStudentEntity.class);
        studentService.saveStudent(studentEntity);
    }
}
