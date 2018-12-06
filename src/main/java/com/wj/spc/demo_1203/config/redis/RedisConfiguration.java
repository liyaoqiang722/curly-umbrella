package com.wj.spc.demo_1203.config.redis;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * Created by liyaoqiang on 2018/12/6.
 */
@Configuration
public class RedisConfiguration {

    @Value("${spring.redis.host}")
    private String hostName;

    @Value("${spring.redis.port}")
    private Integer port;

    @Value("${spring.redis.password}")
    private String password;

    /**
     * JedisConnectionFactory 中注入 redis host port password.
     *
     * @return JedisConnectionFactory对象
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(hostName, port);
        config.setPassword(RedisPassword.of(password));

        JedisClientConfiguration.JedisClientConfigurationBuilder clientConfiguration = JedisClientConfiguration.builder();
        clientConfiguration.connectTimeout(Duration.ofMillis(0));
        JedisConnectionFactory factory = new JedisConnectionFactory(config, clientConfiguration.build());

        return factory;
    }

    @Bean
    public StringRedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer();
    }

    /**
     * RedisTemplate 中注入JedisConnectionFactory.
     *
     * @return RedisTemplate
     */
    @Bean
    public RedisTemplate redisTemplate(@Qualifier("stringRedisSerializer") StringRedisSerializer stringRedisSerializer,
                                       @Qualifier("jedisConnectionFactory") JedisConnectionFactory jedisConnectionFactory) {

        RedisTemplate rt = new RedisTemplate();
        rt.setConnectionFactory(jedisConnectionFactory);
        rt.setKeySerializer(stringRedisSerializer);
        rt.setHashKeySerializer(stringRedisSerializer);
        rt.setHashValueSerializer(stringRedisSerializer);
        rt.setValueSerializer(stringRedisSerializer);

        return rt;
    }
}

