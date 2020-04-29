package cn.e3mall.cart.interceptor;

import cn.e3mall.common.util.CookieUtils;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录拦截器.
 * 就是为了判断用户是否已经登录
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Value("${user.token.key}")
    private String userToken;
    @Value("user.key.in.request")
    private String keyOfUserInRequest;
    @Autowired
    private TokenService tokenService;
    /**
     * 前处理。执行handler之前执行
     * 1 先从cookie中获取token。
     * 2没有token就是未登录状态，直接放行。
     * 3如果取到token，根据token获取用户信息。
     * 4如果没有取到用户信息，说明登录过期，直接放行。
     * 5取到了用户信息，说明是登录状态，将用户信息放到request中。只需要在controller中判断request中是否包含用户信息
     * 2 放行，怎么地都得放行
     * @param request
     * @param response
     * @param handler 目标方法
     * @return 返回true就是放行，返回false就是拦截
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = CookieUtils.getCookieValue(request, userToken);
        if(StringUtils.isNotBlank(token)){
            E3Result result = tokenService.getUserByToken(token);
            if(result.getStatus() ==200){
                TbUser user = (TbUser) result.getData();
                request.setAttribute(keyOfUserInRequest,user);
            }
        }
        return true;
    }

    /**
     * handler执行之后，返回modelAndView之前
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 返回modelAndView之后，可以再此处处理异常
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
