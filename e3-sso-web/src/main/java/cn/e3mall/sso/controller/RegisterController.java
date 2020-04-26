package cn.e3mall.sso.controller;

import cn.e3mall.common.util.E3Result;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @RequestMapping("/user/check/{param}/{type}")
    public E3Result checkData(@PathVariable String param, @PathVariable Integer type){
        E3Result result = registerService.checkData(param, type);
        return result;
    }


    @RequestMapping(value="/user/register",method = RequestMethod.POST)
    public E3Result register(TbUser tbUser){
        E3Result result = registerService.createUser(tbUser);
        return result;
    }
}
