package cn.e3mall.sso.service.impl;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.common.util.JsonUtils;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private JedisClient jedisClient;
    @Value("${user.info.redis.prefix}")
    private String userInfoRedisPrefix;
    @Value("${session.expire}")
    private Integer sessionExpire;
    @Override
    public E3Result getUserByToken(String token) {
        String json = jedisClient.get(userInfoRedisPrefix + ":" + token);
        if(StringUtils.isBlank(json)){
            return E3Result.build(400,"用户登录已经过期，请重新登录");
        }
        jedisClient.expire(userInfoRedisPrefix + ":" + token,sessionExpire);
        TbUser tbUser = JsonUtils.jsonToObject(json, TbUser.class);
        return E3Result.ok(tbUser);
    }
}
