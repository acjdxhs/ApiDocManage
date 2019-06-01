package com.scut.backend.uits;

import com.scut.backend.model.TokenSession;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisUtil {
    private static RedisTemplate<String, Object> template;
    private static final Integer HTTP_ACCESS_TOKEN_EXPIRE_INTERVAL = 2 * 60 * 60;

    static {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.afterPropertiesSet();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //将刚才的redis连接工厂设置到模板类中
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.afterPropertiesSet();
        template=redisTemplate;
    }

    public static TokenSession getTokenSession(String token){
        TokenSession tokenSession = (TokenSession) template.opsForValue().get(token);
        return  tokenSession;
    }

    public static String setTokenSession(Integer uid){
        String token = UuidUtil.getUUID();
        TokenSession tokenSession = new TokenSession(token, uid);
        template.opsForValue().set(token,tokenSession, HTTP_ACCESS_TOKEN_EXPIRE_INTERVAL, TimeUnit.SECONDS);
        return token;
    }

    public static void updateToken(String token){
        template.expire(token,HTTP_ACCESS_TOKEN_EXPIRE_INTERVAL,TimeUnit.SECONDS);
    }

}
