package cn.e3mall.sso.service;

import cn.e3mall.common.util.E3Result;

public interface LoginService {
    /*
     * 1、判断用户和密码是否正确
     * 2、如果不正确，返回登录失败
     * 3、如果正确生成token。
     * 4、把用户信息写入redis，key：token value：用户信息
     * 5、设置Session的过期时间
     * 6、把token返回
     */
    //返回值：E3Result，其中包含token信息
    E3Result userLogin(String username, String password);

    /**
     *  1 删除redis中的token
     *  2 cookie中的token设置为null
     *  3 session中用户信息设置为null
     * @return
     */
    E3Result userLogout(String token);
}
