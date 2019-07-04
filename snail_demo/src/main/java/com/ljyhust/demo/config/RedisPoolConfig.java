package com.ljyhust.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisPoolConfig {

    @Value("${redisCluster.url}")
    private String redisClusterUrl;

    @Value("${redisCluster.port}")
    private Integer redisClusterPort;

    @Value("${redisCluster.timeout}")
    private Integer redisClusterTimeout;

    @Value("${redisCluster.auth}")
    private String redisClusterAuth;

    @Bean
    public JedisPool jedisPoolClusterClient(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(50);
        jedisPoolConfig.setMaxWaitMillis(3000);
        jedisPoolConfig.setMaxTotal(150);
        JedisPool jedisPool =new JedisPool(
                jedisPoolConfig,
                redisClusterUrl,
                redisClusterPort,
                redisClusterTimeout,
                redisClusterAuth);
        return jedisPool;
    }
}
