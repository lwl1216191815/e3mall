package cn.e3mall.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/page/register")
    public String showRegister(){
        return "register";
    }

    @RequestMapping(value = {"/page/login","/user/login"})
    public String showLogin(){
        return "login";
    }
}
