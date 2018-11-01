package com.sims.Controller;


import com.alibaba.fastjson.JSONObject;
import com.sims.Entity.TStudentEntity;
import com.sims.Service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
    @RequestMapping(value = "getStudentByCondition.action")
    public Page<TStudentEntity> getStudent(Integer limit, Integer page){
        Pageable pageable = new PageRequest(page-1,limit);
        return studentService.getStudent(pageable);
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
        Map<String,Object> params = JSONObject.parseObject(json, Map.class);
        studentService.modifyStudent(params);
    }


    /**
     *
     * @param ids
     */
    @RequestMapping(value = "removeStudent.action")
    public void removeStudent(String[] ids){
        studentService.removeStudent(ids);
    }
}
