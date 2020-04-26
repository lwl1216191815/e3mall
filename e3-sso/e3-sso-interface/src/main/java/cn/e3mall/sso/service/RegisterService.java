package cn.e3mall.sso.service;

import cn.e3mall.common.util.E3Result;
import cn.e3mall.pojo.TbUser;

public interface RegisterService {

    /**
     * 数据校验,一个用户只能对应一个username、phone、email
     * @param param
     * @param type 1 代表username 2 代表phone 3 代表email
     * @return
     */
    E3Result checkData(String param,int type);

    /**
     * 用户注册
     * @param tbUser
     * @return
     */
    E3Result createUser(TbUser tbUser);
}
