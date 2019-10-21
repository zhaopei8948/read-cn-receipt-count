package com.github.readcnreceiptcount.config;

import com.github.readcnreceiptcount.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.PostConstruct;

@Configuration
public class CommonConfig {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostConstruct
    public void initProp() {
        RedisUtils.setStringRedisTemplate(this.stringRedisTemplate);
    }
}
