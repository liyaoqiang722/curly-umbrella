package com.wj.spc.demo_1203.dao.redis.impl;

import com.wj.spc.demo_1203.commons.util.SerializableUtils;
import com.wj.spc.demo_1203.dao.redis.TestDao;
import com.wj.spc.demo_1203.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestDaoImpl implements TestDao {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<User> getUsers() {
        return (List<User>) redisTemplate.execute(new RedisCallback<List<User>>() {
            @Override
            public List<User> doInRedis(RedisConnection connection) throws DataAccessException {

                byte[] key = redisTemplate.getStringSerializer().serialize("users");

                byte[] value = connection.get(key);

                return (List<User>) SerializableUtils.unSerialize(value);
            }
        });
    }

    @Override
    public void saveUsers(List<User> list) {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {

                byte[] bKey = redisTemplate.getStringSerializer().serialize("users");

                byte[] bValue = SerializableUtils.serialize(list);

                connection.set(bKey, bValue);

                return null;
            }
        });
    }
}
