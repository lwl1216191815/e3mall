package cn.e3mall.sso.service.impl;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.common.util.JsonUtils;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${user.info.redis.prefix}")
    private String userInfoRedisPrefix;
    @Value("${session.expire}")
    private Integer sessionExpire;
    @Override
    public E3Result userLogin(String username, String password) {
        TbUserExample userExample = new TbUserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<TbUser> userList = tbUserMapper.selectByExample(userExample);
        if(userList != null && !userList.isEmpty()){
            TbUser user = userList.get(0);
            if(user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
                //登录成功后生成token
                String token = UUID.randomUUID().toString();
                jedisClient.set(userInfoRedisPrefix + ":" + token, JsonUtils.objectToJson(user));
                jedisClient.expire(userInfoRedisPrefix + ":" + token,sessionExpire);
                return E3Result.ok(token);
            }
        }
        return E3Result.build(400,"用户名或者密码错误");
    }
}
