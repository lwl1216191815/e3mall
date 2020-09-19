package cn.e3mall.service.config.spring;

import cn.e3mall.common.jedis.JedisClientPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import redis.clients.jedis.JedisPool;

/**
 * spring配置redis
 */
@PropertySource({"classpath:conf/resource.properties"})
@Configuration
public class RedisApplicationContextConfig {
    @Autowired
    private Environment environment;

    /**
     * 单机版redis连接
     * @return
     */
    @Bean
    public JedisPool jedisPool(){
        String url = environment.getProperty("redis.url");
        String port = environment.getProperty("redis.port");
        JedisPool jedisPool = new JedisPool(url,Integer.valueOf(port));
        return jedisPool;
    }

    /**
     * 单机版redis实现类
     * @return
     */
    @Bean
    public JedisClientPool jedisClientPool(){
        JedisClientPool jedisClientPool = new JedisClientPool();
        JedisPool jedisPool = jedisPool();
        jedisClientPool.setJedisPool(jedisPool);
        return jedisClientPool;
    }
}
