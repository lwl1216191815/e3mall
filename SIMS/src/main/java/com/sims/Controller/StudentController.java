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
 * 学生管理控制层
 * @author 龙帅
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

    /**
     * 增加学生信息
     * @param json
     */
    @RequestMapping(value = "saveStudent.action")
    public void saveStudent(String json){
        TStudentEntity studentEntity = JSONObject.parseObject(json, TStudentEntity.class);
        studentService.saveStudent(studentEntity);
    }

    /**
     * 修改学生信息
     * @param json
     */
    @RequestMapping(value = "modifyStudent.action")
    public void modifyStudent(String json){
        TStudentEntity studentEntity = JSONObject.parseObject(json, TStudentEntity.class);
        studentService.modifyStudent(studentEntity);
    }
}
