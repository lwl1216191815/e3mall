package cn.e3mall.order.interceptor;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.util.CookieUtils;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.common.util.JsonUtils;
import cn.e3mall.pojo.TbItem;
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
 * 订单系统登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Value("${user.token.key}")
    private String userToken;
    @Value("${sso.url}")
    private String ssoUrl;
    @Autowired
    private TokenService tokenService;
    @Value("${user.key.in.request}")
    private String userInRequest;
    @Value("${cart.cookie.key}")
    private String cartCookie;
    @Autowired
    private CartService cartService;
    /**
     * 拦截前执行
     * 1、从cookie中取token
     * 2、如果没有取到，没有登录，跳转到sso系统的登录页面。拦截
     * 3、如果取到token。判断登录是否过期，需要调用sso系统的服务，根据token取用户信息
     * 4、如果没有取到用户信息，登录已经过期，重新登录。跳转到登录页面。拦截
     * 5、如果取到用户信息，用户已经是登录状态，把用户信息保存到request中。放行
     * 6、判断cookie中是否有购物车信息，如果有合并购物车
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //先从cookie中拿到token
        String token = CookieUtils.getCookieValue(request, userToken);
        //如果token没有值，就跳转到登录页面，并且拦截
        if(StringUtils.isBlank(token)){
            response.sendRedirect(ssoUrl + "page/login?redirect=" + request.getRequestURL());
            return false;
        }
        //根据token获取用户
        E3Result result = tokenService.getUserByToken(token);
        //如果没有取到用户，说明登录已经过期，需要重新登录，跳转到登录页面，拦截
        if(result.getStatus() != 200){
            response.sendRedirect(ssoUrl + "page/login?redirect=" + request.getRequestURL());
            return false;
        }
        //拿到用户信息，把用户信息放到request中
        TbUser user = (TbUser) result.getData();
        request.setAttribute(userInRequest,user);

        String cartCookie = CookieUtils.getCookieValue(request, this.cartCookie);
        if(StringUtils.isNotBlank(cartCookie)){
            cartService.mergeCart(user.getId(), JsonUtils.jsonToList(cartCookie, TbItem.class));
            CookieUtils.deleteCookie(request,response,this.cartCookie);
        }
        return true;
    }

    /**
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
     *
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
