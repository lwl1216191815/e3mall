package cn.e3mall.sso.controller;

import cn.e3mall.common.util.CookieUtils;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Value("${user.token.key}")
    private String tokenKey;

    @RequestMapping(value="/user/login", method= RequestMethod.POST)
    public E3Result login(String username, String password, HttpServletRequest request, HttpServletResponse response){
        E3Result result = loginService.userLogin(username, password);
        if(result.getStatus() == 200){
            String token = result.getData().toString();
            CookieUtils.setCookie(request,response,tokenKey,token);
        }
        return result;
    }
}
