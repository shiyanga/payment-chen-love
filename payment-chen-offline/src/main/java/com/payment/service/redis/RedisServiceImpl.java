package com.payment.service.redis;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by shi_y on 2017/3/7.
 */
@Service
public class RedisServiceImpl implements RedisService {
    private static String redisCode = "utf-8";

    /**
     *
     */
    public  long del(final String... keys) {
        return (Long) redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                long result = 0;
                for (int i = 0; i < keys.length; i++) {
                    result = connection.del(keys[i].getBytes());
                }
                return result;
            }
        });
    }

    /**
     * @param key
     * @param value
     * @param liveTime 秒
     */
    public void set(final byte[] key, final byte[] value, final long liveTime) {
        redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(key, value);
                if (liveTime > 0) {
                    connection.expire(key, liveTime);
                }
                return 1L;
            }
        });
    }

    /**
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(String key, String value, long liveTime) {
        this.set(key.getBytes(), value.getBytes(), liveTime);
    }

    /**
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        this.set(key, value, 0L);
    }

    /**
     * @param key
     * @param value
     */
    public void set(byte[] key, byte[] value) {
        this.set(key, value, 0L);
    }

    /**
     * @param key
     * @return
     */
    public String get(final String key) {
        return (String) redisTemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return new String(connection.get(key.getBytes()), redisCode);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return "";
            }
        });
    }

    @Override
    public String Setkeys(String pattern) {
        return redisTemplate.keys(pattern).toString();
    }

//    /**
//     * @param pattern
//     * @return
//     */
//    public void Setkeys(String pattern) {
//        return redisTemplate.keys(pattern);
//
//    }

    /**
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return (boolean) redisTemplate.execute(new RedisCallback() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.exists(key.getBytes());
            }
        });
    }

    /**
     * @return
     */
    public java.lang.String flushDB() {
        return redisTemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        }).toString();
    }

    /**
     * @return
     */
    public long dbSize() {
        return (long) redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
    }

    /**
     * @return
     */
    public String ping() {
        return redisTemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {

                return connection.ping();
            }
        }).toString();
    }


    public void set() {

    }


    /**
     * @param key
     * @param map
     * @param liveTime
     * @return
     */
    public String hset(String key, Map<String, String> map, long liveTime) {
        BoundHashOperations<String, String, String> hash = redisTemplate.boundHashOps(key);
        hash.putAll(map);
        if (liveTime > 0) {
            hash.expire(liveTime, TimeUnit.SECONDS);
        }
        return "OK";
    }

    public String hget(String key, String mapKey) {
        BoundHashOperations<String, String, String> hash = redisTemplate.boundHashOps(key);
        return hash.get(mapKey);
    }


//    //添加一个有序的set集合
//    public  String zset(String key,Map<String, String> map)
//    {
//        SetOperations<String, Object> zset = redisTemplate.opsForSet();
//
//        zset.members().;
//
//
//    }


//    public  String opsForSet()
//    {
//
//    }








//
//    //添加一个 key
//    ValueOperations<String, Object> value = redisTemplate.opsForValue();
//        value.set("lp", "hello word");
//    //获取 这个 key 的值
//        System.out.println(value.get("lp"));
//    //添加 一个 hash集合
//    HashOperations<String, Object, Object>  hash = redisTemplate.opsForHash();
//    Map<String,Object> map = new HashMap<String,Object>();
//        map.put("name", "lp");
//        map.put("age", "26");
//        hash.putAll("lpMap", map);
//    //获取 map
//        System.out.println(hash.entries("lpMap"));
//    //添加 一个 list 列表
//    ListOperations<String, Object> list = redisTemplate.opsForList();
//        list.rightPush("lpList", "lp");
//        list.rightPush("lpList", "26");
//    //输出 list
//        System.out.println(list.range("lpList", 0, 1));
//    //添加 一个 set 集合
//    SetOperations<String, Object> set = redisTemplate.opsForSet();
//        set.add("lpSet", "lp");
//        set.add("lpSet", "26");
//        set.add("lpSet", "178cm");
//    //输出 set 集合
//        System.out.println(set.members("lpSet"));
//    //添加有序的 set 集合
//    ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
//        zset.add("lpZset", "lp", 0);
//        zset.add("lpZset", "26", 1);
//        zset.add("lpZset", "178cm", 2);
//    //输出有序 set 集合
//        System.out.println(zset.rangeByScore("lpZset", 0, 2));
//


    @Autowired
    private RedisTemplate redisTemplate;
}