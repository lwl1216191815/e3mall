package cn.e3mall.sso.service;

import cn.e3mall.common.util.E3Result;

public interface TokenService {

    E3Result getUserByToken(String token);
}
