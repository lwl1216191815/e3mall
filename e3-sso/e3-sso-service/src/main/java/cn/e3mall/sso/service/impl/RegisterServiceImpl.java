package cn.e3mall.sso.service.impl;

import cn.e3mall.common.util.E3Result;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.sso.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private TbUserMapper tbUserMapper;
    @Override
    public E3Result checkData(String param, int type) {
        TbUserExample tbUserExample = new TbUserExample();
        TbUserExample.Criteria criteria = tbUserExample.createCriteria();
        switch (type){
            case 1 :
                criteria.andUsernameEqualTo(param);
                break;
            case 2:
                criteria.andPhoneEqualTo(param);
                break;
            case 3:
                criteria.andEmailEqualTo(param);
                break;
                default:
                    return E3Result.build(400,"参数有问题");
        }
        List<TbUser> userList = tbUserMapper.selectByExample(tbUserExample);
        if(userList != null && !userList.isEmpty()){
            return E3Result.ok(false);
        }
        return E3Result.ok(true);
    }
}
