package cn.e3mall.sso.service.impl;

import cn.e3mall.common.util.E3Result;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.sso.service.RegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
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

    @Override
    public E3Result createUser(TbUser tbUser) {
        if(StringUtils.isBlank(tbUser.getUsername())){
            return E3Result.build(400,"用户名不能为空");
        }
        if(StringUtils.isBlank(tbUser.getPassword())){
            return E3Result.build(400,"密码不能为空");
        }
        E3Result result = checkData(tbUser.getUsername(), 1);
        if(!Boolean.valueOf(result.getData().toString())){
            return E3Result.build(400,"此用户名已存在");
        }
        if(StringUtils.isNotBlank(tbUser.getPhone())){
            result = checkData(tbUser.getPhone(),2);
            if(!Boolean.valueOf(result.getData().toString())){
                return E3Result.build(400,"此手机号已被使用");
            }
        }
        if(StringUtils.isNotBlank(tbUser.getEmail())){
            result = checkData(tbUser.getEmail(),3);
            if(!Boolean.valueOf(result.getData().toString())){
                return E3Result.build(400,"此邮箱已被使用");
            }
        }

        tbUser.setCreated(new Date());
        tbUser.setUpdated(new Date());

        String password = DigestUtils.md5DigestAsHex(tbUser.getPhone().getBytes());
        tbUser.setPassword(password);

        tbUserMapper.insert(tbUser);
        return E3Result.ok();
    }
}
